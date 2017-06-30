# SESL (SilenceEngine Shading Language)

SESL is a new shading language that addresses the differences between GLSL, GLSL ES and GLSL for WebGL, and also provide experimental support for implementing libraries of shading functions and techniques by implementing packages. Though currently this is in the design phase, and is being implemented as a college final year project, I'm planning to use it in future with SilenceEngine, and hence the name.

**Though this is a new shading language, I plan to implement this compiler as a transpiler. This tool outputs valid GLSL or GLSL ES code that you could use in your OpenGL programs just as you would normally use GLSL shaders.**

I had to say that the syntax of SESL will be very similar to the syntax of GLSL, that you find the very same data types. However it is an extension to it, that it adds packages, inheritance (you can extend shaders to derive new shaders in SESL), structs will have methods attached to them (they also have constructors), and a whole lot of other features which you'll be introduced to later in this document.

## Hello SESL

Here's your very first taste of SESL. To be simple, I implement a shader that just does nothing, or better produces a single dot in the center in red color.

~~~sesl
import sesl.Color;

shader vert MyShader {
    vec4 main() {
        return vec4(0.5, 0.5, 0.0, 1.0);
    }
}

shader frag MyShader {
    vec4 main() {
        return Color.RED;
    }
}
~~~

As you can see, the syntax of SESL is very similar to C, that it belongs to C family of languages just like GLSL is. However it is much more high level than GLSL. You will have a lot of predefined constants as well (such as `Color.RED` you saw in the above example).

## High Level Language

SESL offers a high level language that it is possible to extend shaders to derive new shaders. For example, you can extend `MyShader` and return a blue color. Or you can even blend the outputs too.

~~~sesl
import sesl.Math;
import sesl.Color;

shader frag MyNewShader extends MyShader {
    vec4 main() {
        return Math.mix(Color.BLUE, super.main(), 0.5);
    }
}
~~~

In this example, we are mixing the color outputted by the `MyShader` with the blue color, and outputting it instead.

## Use of Libraries

The following is a good example that shows how to use the standard library to implement a phong shader. All you have to do is provide it with the required inputs and it will take care of everything.

~~~sesl
// Import shader functions from libraries
import sesl.phong.*;
import sesl.lights.*;

// Declare the VertexShader called as MyShader. This will be
// compiled to the output file MyShader.vert.glsl
shader vert MyShader
{
    uniform mat4 mv;
    uniform mat4 mvp;

    in vec3 position;

    // Pass variables are similar to inputs, but they are automatically
    // passed to the next shader stage.
    pass vec3 normal;
    pass Material material;
    pass PointLight pointLight;

    // Additional outputs can be added here
    out vec3 vertex;

    // The main shader function. Note that we avoid having a gl_ variables.
    // The return value of the main function will be automatically assigned
    // to gl_Position in GLSL target.
    vec4 main()
    {
        vert = vec3(mv * vec4(position, 1.0));
        return mvp * vec4(position, 1.0);
    }
}

// Declare the FragmentShader called as MyShader. This will be
// compiled to the output file MyShader.frag.glsl
shader frag MyShader
{
    // out variables of vertex shader as well as the pass variables
    // are in variables in the fragment shader here.
    in vec3 vertex;
    in vec3 normal;

    in Material material;
    in PointLight pointLight;

    // The main shader function. Older GLSL has gl_FragColor and in modern
    // GLSL, we have to declare an output of vec4 and assign to that. In
    // SESL, the shader simply returns the fragment color, which will be
    // handled automatically.
    vec4 main()
    {
        // Call the phong function from the sesl.phong package.
        return phong(pointLight, material, vertex, normal);
    }
}
~~~
