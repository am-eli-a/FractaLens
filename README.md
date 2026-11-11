# FractaLens

Une bibliothèque Java et un outil en ligne de commande pour générer des images de fractales (statiques ou animées) à partir de plusieurs suites complexes et palettes de couleurs.

## Démarrage rapide

```bash
./gradlew run --args="-f MANDELBROT -w 800 -h 600 -s 0.001 -x=-0.75 -y 0 -n 250 -p GRAYSCALE -o mandelbrot.png"
```

Sous Windows (PowerShell), utiliser `.\gradlew`. Sans option (ou avec une option invalide), le message d'aide est affiché.

## Options

| Option | Description | Valeur par défaut |
|--------|-------------|-------------------|
| `-f`, `--fractal-name` | Fractale à générer | *obligatoire* |
| `-p`, `--palette-name` | Palette de couleurs | *obligatoire* |
| `-n`, `--nb-iterations` | Nombre maximal d'itérations par pixel | *obligatoire* |
| `-w`, `--width` / `-h`, `--height` | Taille de l'image en pixels | *obligatoire* |
| `-s`, `--scale` | Taille d'un pixel dans le plan complexe | *obligatoire* |
| `-x`, `--focus-x` / `-y`, `--focus-y` | Centre de l'image (partie réelle / imaginaire) | *obligatoire* |
| `-o`, `--output` | Fichier de sortie (`.png` ou `.gif`) | *obligatoire* |
| `-t`, `--type` | `SINGLE`, `ZOOM` ou `ROTATION` | `SINGLE` |
| `-i`, `--image-count` | Nombre d'images (animations) | `50` |
| `-z`, `--zoom-factor` | Facteur appliqué à l'échelle entre deux images d'un `ZOOM` (entre 0 et 1) | `0.9` |
| `-d`, `--delay` | Délai entre deux images (ms) | `100` |
| `--c-real`, `--c-imaginary` | Constante c de la suite | `-0.4`, `0.6` |
| `--help` | Affiche le message d'aide | |

Remarques :
- `-h` correspond à la hauteur, l'aide n'est donc disponible que via `--help`.
- Les valeurs négatives s'écrivent avec `=` (ex. `-x=-0.75`).
- Les noms des fractales, palettes et types sont sensibles à la casse.
- Les animations sont lourdes : garder des images petites (de 200×200 à 300×300).

## Fractales et palettes

**Fractales** (`-f`) : `JULIA`, `MANDELBROT`, `MULTI_JULIA`, `MULTIBROT`, `BURNING_SHIP`, `GENERALIZED_BURNING_SHIP`, `TRICORN`, `GENERALIZED_TRICORN`, `CUBIC_JULIA`, `MIXED_JULIA_MANDELBROT`.

**Palettes** (`-p`) : `GRAYSCALE`, `GRADIENT`, `FIXED`, `FIRE`, `NIGHT`, `FOREST`, `INVERTED_CYAN`.

Une animation `ROTATION` utilise toujours la suite de Julia, dont la constante initiale est donnée par `--c-real` et `--c-imaginary`.

## Exemples

```bash
# Zoom sur l'ensemble de Mandelbrot
./gradlew run --args="-f MANDELBROT -t ZOOM -i 120 -w 200 -h 200 -s 0.015 -x=-0.743643887037151 -y 0.131825904205330 -n 500 -p GRADIENT -z 0.92 -d 80 -o mandelbrot-zoom.gif"

# Rotation de l'ensemble de Julia
./gradlew run --args="-f JULIA -t ROTATION -i 72 -w 200 -h 200 -s 0.016 -x 0 -y 0 -n 50 -p FIXED --c-real 0.7885 --c-imaginary 0 -d 60 -o julia-rotation.gif"
```

Les commandes permettant d'obtenir toutes les images de la SAÉ (sprints 2 à 6) sont listées dans la documentation complète.

## Animations du concours

La classe `ContestAnimations` (lancer son `main` depuis l'IDE) génère trois animations qui ne sont pas toutes descriptibles avec la ligne de commande :

- `contest-julia.gif` : rotation complète de la constante de Julia, à partir de c = 0,7885 (palette `FOREST`).
- `contest-mandelbrot.gif` : zoom infini et sans coupure sur le point de Feigenbaum (zoom du facteur δ ≈ 4,669 avec le nombre d'itérations doublé).
- `contest-free.gif` : rotation complète de la constante du Burning Ship généralisé (puissance 3, |c| = 0,6).

## Architecture

La bibliothèque est découpée en packages :

- `complex` : nombres complexes (`IComplex`) et plan complexe, avec des décorateurs de zoom et de translation.
- `complex.sequence` : suites (`IComplexSequence`), leur itérateur, la fabrique `ComplexSequences` et l'énumération `ComplexSequenceType`.
- `image.palette` : palettes (`IPalette`) comme le niveau de gris, le dégradé et les palettes fixes, avec des décorateurs de masque et d'inversion, listées dans `PaletteType`.
- `image` : `IFractalImage`, adossée à une `BufferedImage`, et `ImageType`.
- `generator` : `FractalGenerator` (image seule), `CompositeFractalGenerator` (animations) et `FractalGeneratorBuilder`.
- `application` : l'interface en ligne de commande `picocli` (`Main`, `FractaLensCommand`) et `ContestAnimations`.

Design patterns utilisés : Strategy (suites, palettes), Decorator (plan, palettes), Iterator, Static Factory Method, Adapter (`BufferedImage`), Composite (animations), Builder (configuration du générateur) et Facade (ligne de commande).

## Qualité

- Tests : `./gradlew test` (rapport de couverture dans `build/reports/jacoco/test/html/index.html`)
- Analyse statique : `./gradlew sonar -Dsonar.token=<votre token>`

## Contributeurs

- [Nathan Salomé](https://github.com/ZRekso)
- [Amelia Dufour-Lazrek](https://github.com/am-eli-a)
- [Baptiste Wagée](https://github.com/baptiste-wagee)
- [Matty Fourmestraux](https://github.com/Racerevo)
