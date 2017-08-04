# SESL (SilenceEngine Shading Language) v1.0

## 1. Introduction

### 1.1. Overview

This document describes a programming language called _SilenceEngine Shading Language_, or _seslang_. OpenGL now offers a shading language called as _OpenGL Shading Language_ which allows a user to program the OpenGL graphics pipeline. However, there are different versions depending on the target, it is different for GLSL ES, for WebGL, etc., SilenceEngine Shading Language is intended to solve this issue, and is being developed for use in [SilenceEngine](https://silenceengine.goharsha.com).

Just like in GLSL, independent compilable units in this language are called _shaders_, and a _program_ is a set of shaders that are compiled and linked together. All the concepts are similar to GLSL, which is the target output language of the SESL transpiler. However, SESL adds the concepts of inheritance, packages, and some basic OOP to enhance the shaders.

All the features from standard GLSL are the same, and so is the syntax. The GLSL built-in functions are now instead not built-in but native functions in the standard library. Additionally there are now _pass_ variables which are automatically passed to the next shader stage in the program.

### 1.2. Motivation

Due to having differences in the features that are supported across devices of varying hardware features, and the availability of different extensions depending on the drivers sometimes lead to a case where we need to have same shader written in different versions of the language, which leads to complexity in maintenance. And also some other things, like you have to use built-ins such as `gl_FragColor` in GLSL ES, but you have to declare one yourself in desktop GLSL. SESL addresses those problems by having a single language which is automatically transpiled to GLSL based on the target version.

Due to the lack of methods that allow to import from other library files, it is not possible to have re-usable code in GLSL. SESL improves that by allowing shaders to import functions from other packages, making it easy for the developers to reuse more code.

Since GLSL is compiled at runtime, it is not possible to check for shader errors until we compile and link the shaders. That will not be a limitation anymore as the transpiler will point out most of the errors right at compile time, making it more easy to spot the issues.

### 1.3. Design Considerations

The SESL language is designed to be a even higher and more portable version of GLSL, and is designed to make the use of SilenceEngine more easier and offer more portability. The language is designed with having a library in the mind, as we think that just like any other language, shading languages can also contain a standard library, instead of having everything in the global state.

The complete language and compiler (we call it a transpiler) is a command line tool, and it never interferes the graphical application at runtime. We offer no change in the runtime, and there will be no impact on the performance. The programs that you write in SESL will get translated by the transpiler to GLSL, with one big uber-shader that handles all the issues of different GLSL versions. Using `#ifdef`s we emit output GLSL that is more platform consistent. You can safely use the same shader file in desktop GL > 3.3, or in Android with OpenGL ES > 3.0 and WebGL 1 & 2.

SESL comes with the preprocessor which offers complete directives of the GLSL preprocessor version 450. The custom defines can be either made in code, or from command-line options.

### 1.4. Error Handling

Having a transpiler for the shaders allows for complete error handling at compile time. In SESL, we go for complete error checking. That makes us find the ill formed code very early, and also speed up the debugging process. We make the compiler gives much accurate error messages, with exact line and column numbers and often most of the time pointing at the exact lexeme where the error occurred.

### 1.5. Typographical Conventions

In this document, we use some typographical conventions. All the terms that are concepts of the language will be _italicized_ and we use `monospace` for code elements. The clarifying grammar segments in the document will be displayed using **bold** typeface for the terminals, and in the _italics_ for the non-terminals.

## 2. Overview of SESL Shading

_SilenceEngine Shading Language_ shares a lot of common features when compared to the _OpenGL Shading Language_, much to the fact that SESL is built on top of GLSL. SESL translates directly to the GLSL, so it is GLSL code that you are running in the end. SESL however adds some additional concepts on top of the GLSL concepts.

The code is split among different files which are contained in different packages, and hence they reside in the filesystem. There is a standard library as well, which organizes the built-in GLSL native functions and types. The core of GLSL are present in the `sesl.lang` package which is implicitly imported into every file.

The shaders define which type they are, by using `vert` or `frag` keywords after the `shader` keyword, which is followed by a name to identify the shader. These shaders can extend other shaders, and some of them can be `abstract` as well. The only difference between abstract shaders is that they aren't translated to GLSL unless some other shader which is non-abstract extends from it.

The output is written to files of the form _shader_name_**.**_shader_type_**.**_glsl_ into the output directory. In case these are present in a package, then a package directory is created in the output directory which will contain the shader files. The output GLSL files will work in any selected target GLSL version that was given before the transpilation.

## 3. Basics

### 3.1. Character Set

The source set of the SilenceEngine shading language is the same as the source set of the OpenGL Shading Language, and is a subset of the ASCII character set. The following are the symbols allowed in the language:

  - The letters **a-z** and **A-Z**, and the underscore **_**.
  - The numbers **0-9**.
  - The symbols period **(.)**, plus **(+)**, dash **(-)**, slash **(/)**, asterisk **(\*)**, percent **(%)**, angled brackets **(\< and \>)**, square brackets **( \[ and \] )**, parentheses **( \( and \) )**, braces **( \{ and \} )**, caret **(^)**, vertical bar **( | )**, ampersand **(&)**, tilde **(~)**, equals **(=)**, exclamation point **(!)**, colon **(:)**, semicolon **(;)**, comma **(,)**, and question mark **(?)**.
  - The number sign **#** for preprocessor use.
  - Whitespace _spaces_, _tabs_, _newlines_, _line feeds_, _carriage returns_ etc.,
  
### 3.2. Preprocessor

SESL comes with a preprocessor, that can be used just like the GLSL preprocessor. We have also simplified the number of supported directives, as some of them will have no meaning given the nature of SESL, like for example, `__FILE__` because SESL works with actual files, where as in GLSL, a file is a source string passed to the GLSL compiler, and is just an integer.

The complete list of preprocessor directives are as follows:

  - `#define`
  - `#undef`
  - `#ifdef`
  - `#ifndef`
  - `#else`
  - `#elif`
  - `#endif`
  
There are also the following predefined macros for determining the target GLSL or GLSL ES version:

  - `GLSL_VERSION`
  - `GLSL_CORE`
  - `GLSL_ES`
  
These allow for us to check for the runtime GLSL version. You can use this to select any optional extensions in your shaders depending on the targets. An example will be as follows:

~~~sesl
#if GLSL_ES && GLSL_VERSION == 320
    // Code for GLSL ES 3.20
#endif
~~~

Additionally there is support for the `defined` operator in the preprocessor conditional. Another difference is that unlike GLSL, SESL doesn't permit you to have whitespace after the `#` symbol. The preprocessor is targeted on each file in the compile path individually, and each file will gets it's own preprocessor context. So the defines you made in one file will not be available in the other, as it makes no sense to mix preprocessor directives with the imports of packages.

Also all the preprocessor directives should be on their own line, and shouldn't be mixed with code.

### 3.3. Comments

SESL supports C style comments, both the multi-line comments and also the single line comments. Similar to the comments in GLSL, the comments in SESL also cannot be nested. They are not even considered for the tokens, they are removed right in the lexical analysis. If the comment lies in between a line, it is just considered as a single space.

### 3.4. Tokens

SESL language is a series of tokens, and a token can be any one of the following:

  - Keyword
  - Identifier
  - Integer Constant
  - Floating Constant
  - Operator
  
Every input file is first split into these tokens. We can say that these are the most primitive elements of the SESL language.

### 3.5. Keywords

The following are the keywords of this language, and they cannot be used for any other purpose other than what is described in this document:

|||||||
|---------|---------|------------|----------|----------|---------|
| package | import  | shader     | class    | struct   | extends |
| in      | pass    | out        | if       | else     | switch  |
| case    | default | break      | continue | do       | while   |
| for     | this    | super      | native   | operator | return  |
| discard | const   | uniform    | vert     | frag     | public  |
| private | static  | deprecated |          |          |         |

These are the first class keywords that are supported in SESL. However some additional keywords are reserved for the future use either by GLSL or SESL that are described here:

|||||||
|-----------|-----------------|-------------|-------------|--------------|------------|
| asm       | union           | enum*       | typedef     | template     | packed     |
| goto      | inline*         | noinline    | volatile    | extern       | external   |
| interface | long            | short       | double      | half         | fixed      |
| unsigned  | input           | output      | sizeof      | cast         | namespace  |
| using     | inout           | layout      | precision** | attribute**  | varying**  |
| lowp**    | mediump**       | highp**     | centroid**  | invariant**  | flat**     |
| smooth**  | noperspective** | common      | partition   | active       | superp     |
| filter    | row_major       | patch**     | sample**    | subroutine** | uint**     |
| tessc     | tesse           | geom        | buffer**    | shared**     | coherent** |
| restrict**| readonly**      | writeonly** | precise**   | compute      | resource** |

The keywords marked with __\*__ are reserved by GLSL, but planned to be implemented in SESL in the future versions. In addition, all identifiers that start with two underscores ( **\_\_** ) are reserved as future possible keywords.

The keywords marked with __\*\*__ are existing keywords in GLSL but not supported in SESL. They either have alternate keywords in SESL or not yet supported or even they are removed features of the language.

### 3.6. Identifiers

Identifiers are lexemes that are used as names for objects of structs, classes, shaders, variables, constants, functions, and what not, everything. The rules for naming identifiers in SESL are as follows:

  - Should start with **\[a-zA-Z\_\]**, that is, either an alphabet or an underscore.
  - Cannot contain spaces in between the identifier.
  - Numbers can be present in identifier, but it should not be the starting character.
  - The length of the identifier should not exceed 256 characters.
  
You can have the grammar for the identifier as follows:

~~~
IDENTIFIER = LETTER ( LETTER | DIGIT )* ;
LETTER     = [a-zA-Z_] ;
DIGIT      = [0-9] ;
~~~

Anything of this pattern in SESL is identified by the scanner as an _IDENTIFIER_. The identifiers that are starting with `gl_` are reserved by GLSL, and hence the use of those names as identifiers for declaring variables or constants or shaders or functions is strictly prohibited.

## 4. Variables and Types

All the variables should be declared before the control reaches their access in the current scope or in any outer scope. However having global variables isn't permitted. Variables can only be declared in shaders, functions, structs or classes. No other place can declare variables in SESL.

Functions however, doesn't need to be declared before they were used, the declarations will be made implicitly by the SESL transpiler. Global functions still aren't supported, they can only be part of shaders or classes or structs. There are no default types, that all functions must have a return value or void.

User defined types may be created by using structs or classes or shaders. However only structs can be passed between shaders. SESL is a completely type safe language, where implicit conversions only exist from lower types to higher types, that is only when the conversion will not generate a loss of precision.

### 4.1. Basic Types

Unlike other languages, types in SESL aren't keywords, they are created in code of the standard library using native structs. Even `void`, `int`, etc., are all defined in SESL code in the `sesl.lang` package. The basic types are as follows:

| Type              | Description                                               |
|-------------------|-----------------------------------------------------------|
| `void`            | For functions that do not return a value                  |
| `bool`            | Boolean type, either `true` or `false`                    |
| `int`             | A signed integer type                                     |
| `float`           | A single floating-point scalar type                       |
| `vec2`            | A two component floating point vector                     |
| `vec3`            | A three component floating point vector                   |
| `vec4`            | A four component floating point vector                    |
| `bvec2`           | A two component Boolean vector                            |
| `bvec3`           | A three component Boolean vector                          |
| `bvec4`           | A four component Boolean vector                           |
| `ivec2`           | A two component integer vector                            |
| `ivec3`           | A three component integer vector                          |
| `ivec4`           | A four component integer vector                           |
| `mat2`            | A 2×2 floating-point matrix                               |
| `mat3`            | A 3×3 floating-point matrix                               |
| `mat4`            | A 4×4 floating-point matrix                               |
| `sampler1D`       | A handle for accessing a 1D texture                       |
| `sampler2D`       | A handle for accessing a 2D texture                       |
| `sampler3D`       | A handle for accessing a 3D texture                       |
| `samplerCube`     | A handle for accessing a cube mapped texture              |
| `sampler1DShadow` | A handle for accessing a 1D depth texture with comparison |
| `sampler2DShadow` | A handle for accessing a 2D depth texture with comparison |

These are the most primitive types that are present in the `sesl.lang` package. In addition, a shader can combine these with arrays, or create custom classes or structs to create complex types. Similar to GLSL, there are no pointer types in SESL.

#### 4.1.1. Void

Functions that do not return any value must be declared as **void**. There is no default function return type. Any function which misses a return type will cause a syntax error.

#### 4.1.2. Booleans

Booleans are logical types, they only have either **true** or **false** as the possible values. They are supported to make conditional execution easier. They can be declared and have an optional initializer.

~~~sesl
bool success;       // Declare success to be a boolean
bool done = false;  // Declare done and initialize it to false
~~~

The right side of the assignment operator should evaluate to a boolean expression. Similarly the expressions for conditional jumps **\( if, for, ?:, switch-case, while, do-while \)** should evaluate to a boolean value.

#### 4.1.3. Integers

SESL provides integer support, but doesn't support all integer operations that work in bits. This is because integer support is weak in most GPUs as they are optimized for floating point operations. At the exception of variables, array subscripts, or referencing texture units, SESL automatically replaces them with floats. When used as part of the expression, they will be implicitly converted to a float when required.

~~~sesl
int i, j = 42;
~~~

Literal integers can be expressed in base 10 (decimal), base 8 (octal), base 16 (hexadecimal) and also in base 2 (binary) notations as seen below.

~~~sesl
int a = 0xA;      // Hexadecimal
int b = 72;       // Decimal
int c = 07;       // Octal
int d = 0b101101; // Binary
~~~

SESL doesn't make any guarantees on the integer overflow, as they are dependent on the hardware. However you can be fine until the integer doesn't exceed the 16-bit precision.

TODO: Complete writing the spec
