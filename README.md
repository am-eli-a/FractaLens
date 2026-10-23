# FractaLens - A Fractal Generator Library Developed in Lens.

This project provides a *Java* library for generating fractal images.
It allows users to choose from a variety of complex sequences, including the
Julia set, Mandelbrot set, and other generalized sequences, to create
static or animated fractal images using different color palettes and
transformations.

## Usage

FractaLens is used from the command line. Every setting of the generated image
is given as an option, so that any fractal can be generated without modifying
the code of the application. The options are read with the
[picocli](https://picocli.info/) library.

### Running the application

From the root of the project, run:

```bash
./gradlew run --args="<options>"
```

On Windows (PowerShell), use `.\gradlew` instead of `./gradlew`.

If no option is given, or if an option is invalid, the application prints the
help message describing all the available options.

### Options

| Option | Argument | Description | Default value |
|--------|----------|-------------|---------------|
| `-f`, `--fractal-name` | `<name>` | The name of the fractal to generate (see the list below). | *required* |
| `-n`, `--nb-iterations` | `<number>` | The maximum number of iterations computed for each pixel. | *required* |
| `-h`, `--height` | `<pixels>` | The height of the image, in pixels. | *required* |
| `-w`, `--width` | `<pixels>` | The width of the image, in pixels. | *required* |
| `-s`, `--scale` | `<zoom>` | The scale applied on the complex plane (the size of a pixel in the plane). | *required* |
| `-x`, `--focus-x` | `<number>` | The real part of the point at the center of the image. | *required* |
| `-y`, `--focus-y` | `<number>` | The imaginary part of the point at the center of the image. | *required* |
| `-p`, `--palette-name` | `<name>` | The name of the color palette to use (see the list below). | *required* |
| `-t`, `--type` | `<type>` | The type of image to generate: `SINGLE`, `ZOOM` or `ROTATION`. | `SINGLE` |
| `-i`, `--image-count` | `<number>` | The number of images of an animation (`ZOOM` or `ROTATION` only). | `50` |
| `-z`, `--zoom-factor` | `<factor>` | The factor applied to the scale between two images of a `ZOOM` animation (between 0 and 1). | `0.9` |
| `-d`, `--delay` | `<ms>` | The delay between two images of an animation, in milliseconds. | `100` |
| `--c-real` | `<number>` | The real part of the constant c of the sequence. | `-0.4` |
| `--c-imaginary` | `<number>` | The imaginary part of the constant c of the sequence. | `0.6` |
| `-o`, `--output` | `<file>` | The name of the file in which the image is saved (`.png` or `.gif`). | *required* |
| `--help` | | Prints the help message and exits. | |

> `-h` is used for the height of the image, so the help message is only
> available through `--help`.

> Negative values must be written with `=`, for example `-x=-0.75`, so that
> they are not mistaken for an option.

> The names of the fractals, palettes and types are case-sensitive.

### Available fractals

The name given to `-f` is the name of a constant of the `ComplexSequenceType`
enumeration:

| Name | Sequence |
|------|----------|
| `JULIA` | z² + c, with c = -0.4 + 0.6i |
| `MANDELBROT` | z² + z₀ |
| `MULTI_JULIA` | z³ + c, with c = -0.4 + 0.6i |
| `MULTIBROT` | z³ + z₀ |
| `BURNING_SHIP` | (\|Re(z)\| + i\|Im(z)\|)² + c, with c = -0.4 + 0.6i |
| `GENERALIZED_BURNING_SHIP` | (\|Re(z)\| + i\|Im(z)\|)³ + c, with c = -0.4 + 0.6i |
| `TRICORN` | conj(z)² + c, with c = -0.4 + 0.6i |
| `GENERALIZED_TRICORN` | conj(z)³ + c, with c = -0.4 + 0.6i |
| `CUBIC_JULIA` | z³ + z² + c, with c = -0.4 + 0.6i |
| `MIXED_JULIA_MANDELBROT` | z² + c × z₀, with c = -0.4 + 0.6i |

The constant c of Julia's sequence can be changed with `--c-real` and
`--c-imaginary`. A `ROTATION` animation always uses Julia's sequence, whose
initial constant is given by these two options.

### Available palettes

The name given to `-p` is the name of a constant of the `PaletteType`
enumeration:

| Name | Palette |
|------|---------|
| `FIXED` | A fixed palette going from white to black, through three shades of blue. |
| `GRADIENT` | A gradient going from dark green to black. |
| `GRAYSCALE` | Shades of gray, from white to black. |

### Examples

A static image of the Mandelbrot set, centered on -0.75:

```bash
./gradlew run --args="-f MANDELBROT -w 800 -h 600 -s 0.001 -x=-0.75 -y 0 -n 250 -p GRAYSCALE -o mandelbrot.png"
```

An animated zoom on the Mandelbrot set:

```bash
./gradlew run --args="-f MANDELBROT -t ZOOM -i 120 -w 200 -h 200 -s 0.015 -x=-0.743643887037151 -y 0.131825904205330 -n 500 -p GRADIENT -z 0.92 -d 80 -o mandelbrot-zoom.gif"
```

An animated rotation of Julia's set, starting from c = 0.7885:

```bash
./gradlew run --args="-f JULIA -t ROTATION -i 72 -w 200 -h 200 -s 0.016 -x 0 -y 0 -n 50 -p FIXED --c-real 0.7885 --c-imaginary 0 -d 60 -o julia-rotation.gif"
```

> Animations can take a long time to generate and use a lot of memory: keep the
> images small (200 × 200 to 300 × 300 pixels).

### Images of the SAÉ

Each image generated during the SAÉ can be obtained with the following commands.

| Sprint | Image | Command |
|--------|-------|---------|
| 2 | Julia, c = -0.4 + 0.6i, gray scale | `-f JULIA -w 800 -h 600 -s 0.004 -x 0 -y 0 -n 100 -p GRAYSCALE -o sprint2.png` |
| 3 | Julia, c = -0.4 + 0.6i, centered on 0.1 + 0.6i | `-f JULIA -w 800 -h 800 -s 0.001 -x 0.1 -y 0.6 -n 250 -p <palette> -o sprint3-1.png` |
| 3 | Julia, c = 0.285 + 0.01i, centered on -0.47 - 0.21i | `-f JULIA --c-real 0.285 --c-imaginary 0.01 -w 800 -h 800 -s 0.001 -x=-0.47 -y=-0.21 -n 250 -p <palette> -o sprint3-2.png` |
| 4 | Mandelbrot, centered on -0.75 | `-f MANDELBROT -w 800 -h 600 -s 0.001 -x=-0.75 -y 0 -n 250 -p <palette> -o sprint4-1.png` |
| 4 | Burning Ship, centered on -1.755 - 0.03i | `-f BURNING_SHIP -w 800 -h 800 -s 0.0001 -x=-1.755 -y=-0.03 -n 64 -p <palette> -o sprint4-2.png` |
| 6 | Zoom on Mandelbrot | see the examples above |
| 6 | Rotation of Julia | see the examples above |

## Overview

This library is composed of the following classes:

```plantuml
hide empty members
skinparam packageStyle rectangle
skinparam classAttributeIconSize 0
left to right direction

' --------------- '
' Complex Numbers '
' --------------- '

package complex {
    interface IComplex {
        + {abstract} getRealPart() : double
        + {abstract} getImaginaryPart() : double
        + {abstract} abs() : double
        + {abstract} negate() : IComplex
        + {abstract} conjugate() : IComplex
        + {abstract} add(other : IComplex) : IComplex
        + {abstract} subtract(other : IComplex) : IComplex
        + {abstract} multiply(value : double) : IComplex
        + {abstract} multiply(other : IComplex) : IComplex
        + {abstract} divide(other : IComplex) : IComplex
        + {abstract} power(p : int) : IComplex
    }

    class Complex {
        - realPart : double {readOnly}
        - imaginaryPart : double {readOnly}
        + Complex(realPart : double, imaginaryPart : double)
        + toString() : String
        + equals(obj : Object) : boolean
        + hashCode() : int
    }

    interface IComplexPlane {
        + {abstract} asComplex(row : int, column : int) : IComplex
    }

    class ComplexPlane {
        - height : int
        - width : int
        + ComplexPlane(height : int, width : int)
    }

    abstract class AbstractPlaneDecorator {
        + AbstractPlaneDecorator(plane : IComplexPlane)
        # getPlane() : IComplexPlane
    }

    class ZoomDecorator {
        - zoom : double {readOnly}
        + ZoomDecorator(zoom : double, plane : IComplexPlane)
    }

    class TranslationDecorator {
        - center : IComplex {readOnly}
        + TranslationDecorator(center : IComplex, plane : IComplexPlane)
    }

    Complex ..|> IComplex
    ComplexPlane ..|> IComplexPlane
    AbstractPlaneDecorator ..|> IComplexPlane
    AbstractPlaneDecorator o--> "1" IComplexPlane : - plane
    ZoomDecorator --|> AbstractPlaneDecorator
    TranslationDecorator --|> AbstractPlaneDecorator
    IComplexPlane ..> IComplex : << creates >>
}

' ----------------- '
' Complex Sequences '
' ----------------- '

package complex.sequence {
    interface "Iterable<IComplex>" as Iterable
    interface "Iterator<IComplex>" as Iterator {
        + {abstract} hasNext() : boolean
        + {abstract} next() : IComplex
    }

    interface IComplexSequence {
        + {abstract} computeNext(previous : IComplex) : IComplex
    }

    class JuliaSequence {
        - first : IComplex {readOnly}
        - max : int {readOnly}
        - c : IComplex {readOnly}
        + JuliaSequence(first : IComplex, max : int, c : IComplex)
    }

    class GenericComplexSequence {
        - z0 : IComplex {readOnly}
        - maxIterations : int {readOnly}
        - operator : BinaryOperator<IComplex> {readOnly}
        + GenericComplexSequence(operator : BinaryOperator<IComplex>, z0 : IComplex, maxIterations : int)
    }

    class ComplexSequenceIterator {
        - current : IComplex
        - maxIterations : int {readOnly}
        - nbIterations : int
        + ComplexSequenceIterator(sequence : IComplexSequence, first : IComplex, maxIterations : int)
    }

    class ComplexSequences << (S,#FF7700) static factory >> {
        + {static} mandelbrot(z0, max) : IComplexSequence
        + {static} multibrot(z0, max, p) : IComplexSequence
        + {static} multiJulia(z0, max, c, p) : IComplexSequence
        + {static} rotatedMultiJulia(z0, max, c, theta, p) : IComplexSequence
        + {static} burningShip(z0, max, c) : IComplexSequence
        + {static} generalizedBurningShip(z0, max, c, p) : IComplexSequence
        + {static} tricorn(z0, max, c) : IComplexSequence
        + {static} tricornGeneral(z0, max, c, p) : IComplexSequence
        + {static} cubicJulia(z0, max, c) : IComplexSequence
        + {static} mixedJuliaMandelbrot(z0, max, c) : IComplexSequence
    }

    enum ComplexSequenceType {
        JULIA
        MANDELBROT
        BURNING_SHIP
        GENERALIZED_BURNING_SHIP
        TRICORN
        GENERALIZED_TRICORN
        MULTI_JULIA
        MULTIBROT
        CUBIC_JULIA
        MIXED_JULIA_MANDELBROT
        + getSequenceFactory() : BiFunction<IComplex, Integer, IComplexSequence>
    }

    IComplexSequence --|> Iterable
    JuliaSequence ..|> IComplexSequence
    GenericComplexSequence ..|> IComplexSequence
    ComplexSequenceIterator ..|> Iterator
    ComplexSequenceIterator --> "1" IComplexSequence : - sequence
    IComplexSequence ..> ComplexSequenceIterator : << creates >>
    ComplexSequences ..> GenericComplexSequence : << creates >>
    ComplexSequenceType ..> ComplexSequences : << uses >>
    ComplexSequenceType ..> JuliaSequence : << uses >>
}

' -------------- '
' Color Palettes '
' -------------- '

package image.palette {
    interface IPalette {
        + {abstract} getColor(nbIteration : int, maxIteration : int) : Color
    }

    class GrayScale
    class GradientPalette {
        - start : Color {readOnly}
        - end : Color {readOnly}
        + GradientPalette(start : Color, end : Color)
    }
    class FixedPalette {
        - colorList : List<Color> {readOnly}
        + FixedPalette(colorList : List<Color>)
    }

    abstract class PaletteDecorator {
        + PaletteDecorator(palette : IPalette)
    }
    class MaskDecorator {
        - red : boolean {readOnly}
        - green : boolean {readOnly}
        - blue : boolean {readOnly}
        + MaskDecorator(palette : IPalette, red : boolean, green : boolean, blue : boolean)
    }
    class InversionDecorator {
        + InversionDecorator(palette : IPalette)
    }

    enum PaletteType {
        + getPalette() : IPalette
    }

    GrayScale ..|> IPalette
    GradientPalette ..|> IPalette
    FixedPalette ..|> IPalette
    PaletteDecorator ..|> IPalette
    PaletteDecorator o--> "1" IPalette : # palette
    MaskDecorator --|> PaletteDecorator
    InversionDecorator --|> PaletteDecorator
    PaletteType ..> IPalette : << provides >>
}

' -------------- '
' Image Handling '
' -------------- '

package image {
    class "java.awt.image.BufferedImage" as BufferedImage

    interface IFractalImage {
        + {abstract} getHeight() : int
        + {abstract} getWidth() : int
        + {abstract} setColor(row : int, column : int, color : Color) : void
        + {abstract} saveAs(path : String) : void
        + {abstract} asBufferedImage() : BufferedImage
    }

    class FractalImage {
        + FractalImage(height : int, width : int)
    }

    FractalImage ..|> IFractalImage
    FractalImage *--> "1" BufferedImage : - image

    class BufferedImageAdapter {
        + BufferedImageAdapter(image : BufferedImage)
    }
    BufferedImageAdapter ..|> IFractalImage
    BufferedImageAdapter o--> "1" BufferedImage : - image
}

' ------------------ '
' Fractal Generation '
' ------------------ '

package generator {
    interface IFractalGenerator {
        + {abstract} generateImage() : IFractalImage
        + {abstract} computePixelColor(row : int, column : int) : Color
        + {abstract} saveImage() : void
    }

    class FractalGenerator {
        - nbMaxIteration : int {readOnly}
        - filePath : String {readOnly}
        - sequenceBiFunction : BiFunction<IComplex, Integer, IComplexSequence>
        ~ FractalGenerator(builder : FractalGeneratorBuilder)
    }

    class CompositeFractalGenerator {
        - delay : int {readOnly}
        - outputFile : String {readOnly}
        - images : List<BufferedImage> {readOnly}
        + CompositeFractalGenerator(generators : List<IFractalGenerator>, delay : int, outputFile : String)
    }

    class FractalGeneratorBuilder {
        - sequenceFactory : BiFunction<IComplex, Integer, IComplexSequence>
        - width : int
        - height : int
        - zoom : double
        - maxIterations : int
        - outputFile : String
        - frameCount : int
        - zoomFactor : double
        - frameDelay : int
        - rotationStep : double
        - center : IComplex
        - rotationConstant : IComplex
        + FractalGeneratorBuilder()
        + usingSequence(factory : BiFunction<IComplex, Integer, IComplexSequence>) : FractalGeneratorBuilder
        + usingSequence(type : ComplexSequenceType) : FractalGeneratorBuilder
        + withSize(width : int, height : int) : FractalGeneratorBuilder
        + zoomedBy(zoom : double) : FractalGeneratorBuilder
        + centeredAt(center : IComplex) : FractalGeneratorBuilder
        + withMaxIterations(maxIterations : int) : FractalGeneratorBuilder
        + coloredWith(palette : IPalette) : FractalGeneratorBuilder
        + coloredWith(type : PaletteType) : FractalGeneratorBuilder
        + savedAs(outputFile : String) : FractalGeneratorBuilder
        + animatedWithZoom(factor : double, frames : int) : FractalGeneratorBuilder
        + animatedWithRotation(constant : IComplex, step : double, frames : int) : FractalGeneratorBuilder
        + withFrameDelay(delay : int) : FractalGeneratorBuilder
        + getImage() : IFractalImage
        + getPlane() : IComplexPlane
        + getMaxIterations() : int
        + getPalette() : IPalette
        + getOutputFile() : String
        + getSequenceFactory() : BiFunction<IComplex, Integer, IComplexSequence>
        + build() : IFractalGenerator
        ~ zoomForFrame(initialZoom : double, k : int) : double
        ~ sequenceForFrame(initialSequence : BiFunction<IComplex, Integer, IComplexSequence>, k : int) : BiFunction<IComplex, Integer, IComplexSequence>
    }

    FractalGenerator ..|> IFractalGenerator
    CompositeFractalGenerator ..|> IFractalGenerator
    CompositeFractalGenerator o--> "*" IFractalGenerator : - generators
    FractalGeneratorBuilder ..> FractalGenerator : << creates >>
    FractalGeneratorBuilder ..> CompositeFractalGenerator : << creates >>
}

' ---------------------- '
' Command-Line Interface '
' ---------------------- '

package application {
    class Main {
        + {static} main(args : String[]) : void
    }

    class FractaLensCommand {
        - fractalName : ComplexSequenceType
        - nbIterations : int
        - height : int
        - width : int
        - scale : double
        - focusX : double
        - focusY : double
        - paletteName : PaletteType
        - type : ImageType
        - imageCount : int
        - zoomFactor : double
        - delay : int
        - cReal : double
        - cImaginary : double
        - output : String
        + call() : Integer
    }

    interface "Callable<Integer>" as Callable

    FractaLensCommand ..|> Callable
    Main ..> FractaLensCommand : << runs >>

    enum ImageType {
        SINGLE
        ZOOM
        ROTATION
    }

    FractaLensCommand ..> FractalGeneratorBuilder : << configures >>
    FractaLensCommand ..> ImageType
}

' ------------------------------ '
' Links between the packages     '
' ------------------------------ '

FractalGenerator --> "1" IFractalImage : - image
FractalGenerator --> "1" IComplexPlane : - plane
FractalGenerator --> "1" IPalette : - palette
FractalGeneratorBuilder --> "1" IPalette : - palette
FractalGenerator ..> IComplexSequence : << uses >>
FractalGeneratorBuilder ..> ComplexSequenceType
FractalGeneratorBuilder ..> PaletteType
FractalGeneratorBuilder ..> ZoomDecorator : << creates >>
FractalGeneratorBuilder ..> TranslationDecorator : << creates >>
FractalGeneratorBuilder ..> BufferedImageAdapter : << creates >>
FractaLensCommand ..> ComplexSequenceType : << parses -f >>
FractaLensCommand ..> PaletteType : << parses -p >>
```

## Feature list

| Features                                               | Design Pattern(s) (?)                                   | Author(s) |
|--------------------------------------------------------|---------------------------------------------------------|-----------|
| Complex number                                         | *(none: immutable value object)*                        | Nathan Salomé |
| Default complex plane                                  | Structural: Decorator (concrete component)              | Amélia Dufour-Lazrek |
| Scaled complex plane                                   | Structural: Decorator                                   | Nathan Salomé |
| Translated complex plane                               | Structural: Decorator                                   | Nathan Salomé |
| Sequence of complex numbers                            | Behavioral: Strategy                                    | Baptiste Wagée |
| Iterator over a sequence of complex numbers            | Behavioral: Iterator                                    | Matty Fourmestraux |
| Julia sequence                                         | Behavioral: Strategy (concrete strategy)                | Baptiste Wagée |
| Mandelbrot sequence                                    | Behavioral: Strategy, Creational: Static Factory Method | Amélia Dufour-Lazrek |
| Multijulia sequence                                    | Behavioral: Strategy, Creational: Static Factory Method | Baptiste Wagée |
| Multibrot sequence                                     | Behavioral: Strategy, Creational: Static Factory Method | Amélia Dufour-Lazrek |
| Burning Ship sequences                                 | Behavioral: Strategy, Creational: Static Factory Method | Baptiste Wagée |
| Tricorn sequences                                      | Behavioral: Strategy, Creational: Static Factory Method | Nathan Salomé |
| Generic sequences                                      | Behavioral: Strategy (lambda expressions)               | Amélia Dufour-Lazrek, Matty Fourmestraux |
| Enumeration of the sequences                           | *(none: enumeration of strategies)*                     | Nathan Salomé |
| Color palette                                          | Behavioral: Strategy                                    | Amélia Dufour-Lazrek |
| Grey scale palette                                     | Behavioral: Strategy (concrete strategy)                | Amélia Dufour-Lazrek |
| Red/Green/Blue/... scale palette                       | Structural: Decorator                                   | Amélia Dufour-Lazrek |
| Inverted color palette                                 | Structural: Decorator                                   | Amélia Dufour-Lazrek |
| Gradient-based color palette                           | Behavioral: Strategy (concrete strategy)                | Baptiste Wagée |
| Predefined color palette                               | Behavioral: Strategy (concrete strategy)                | Matty Fourmestraux |
| Enumeration of the palettes                            | *(none: enumeration of strategies)*                     | Amélia Dufour-Lazrek |
| `BufferedImage` used as an `IFractalImage`             | Structural: Adapter                                     | Baptiste Wagée |
| Static image generation                                | Behavioral: Strategy (context)                          | Matty Fourmestraux |
| Scale-based animated image generation                  | Structural: Composite                                   | Nathan Salomé, Baptiste Wagée, Matty Fourmestraux |
| Rotation-based animated image generation               | Structural: Composite                                   | Nathan Salomé, Amélia Dufour-Lazrek, Baptiste Wagée, Matty Fourmestraux |
| Configuration of the fractal generator                 | Creational: Builder                                     | Baptiste Wagée, Matty Fourmestraux |
| Command-line interface                                 | Structural: Facade                                      | Nathan Salomé, Amélia Dufour-Lazrek |

## Quality

- Unit tests: `./gradlew test` (coverage report in `build/reports/jacoco/test/html/index.html`).
- Static analysis: `./gradlew sonar -Dsonar.token=<your token>`.

## Team

This project has been developed by:

- Nathan Salomé
- Amélia Dufour-Lazrek
- Baptiste Wagée
- Matty Fourmestraux