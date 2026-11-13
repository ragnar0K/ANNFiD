# ANNFiD — Artificial Neural Network File Detector (2013)

> **Historical Time Capsule (2013)**: This is a very old project from when I graduated—back when Java and CPU was all we had—four years before *Attention Is All You Need* and LLM Transformers existed.

**ANNFiD** (*Artificial Neural Network File Detect*) is an early experiment in applying classical feedforward Artificial Neural Networks (built with [Encog](https://www.heatonresearch.com/encog/) in Java) to **forensic file type identification of corrupted or headerless files**.

Instead of relying purely on magic bytes or file extensions (which break when headers are damaged or stripped), ANNFiD trains and evaluates ensembles of byte-window neural networks (`8`, `16`, `24`, `32`, `64`, and `128` byte windows) alongside false-positive filter networks (`FPNN`) and threshold rules stored in SQLite (`ANNDB.sqlite`) to classify file fragments such as:

- **TIFF** (uncompressed, LZW, JPEG-in-TIFF, tag structures)
- **JPEG**
- **GIF**
- **BMP**
- **PDF**

---

## What's in the Box (circa 2013)

- **`src/ANNFileDetect/`** — Java Swing GUI for training feedforward networks, inspecting byte-frequency / activation graphs (`JFreeChart`), editing classification thresholds, and scanning unknown/corrupted files.
- **`nnets/` & `ANNDB.sqlite`** — Pre-trained Encog neural network weights (`.eg`) and SQLite threshold rules.
- **`dist/ANNFileDetect.jar`** — Pre-built Java archive from the original 2013 release.

---

## Original 2013 Notes

The program was developed under very tight timeframes as a graduation project:
- Concurrency/multithreading in the Swing UI is rough around the edges (an extra click or restart was occasionally required).
- Kept online as an archive of early 2010s neural network tinkering.
