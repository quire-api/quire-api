# Quire API

This repository contains the source used to build the **[Quire](https://quire.io) API Reference**.

## Overview

The Quire API project compiles annotated source and documentation into a browsable API reference site.

### Other Documents

* [Activity Types](docs/activity_types.md)
* [Change Log](CHANGES.md)

---

## Tools

- Generator: <https://github.com/sourcey/spectacle>

## Prerequisites

- A **JDK** and **Maven** installed. The build downloads Node.js and npm by itself, and Spectacle comes from `tool/vendor`.
- **Docker**, to preview the docs website.

## Build

Generate the static documentation into `dist/` (it runs `git pull`, then `mvn clean compile`):

```bash
./tool/build
```

## Start the Docs Website

Run a local web server (nginx in Docker) for preview:

```bash
./tool/startWebsite
```

It opens <http://localhost:5001/dev/api/> in your browser. Run `./tool/stopWebsite` to stop it.

## Contributing

Issues and pull requests are welcome. Please:

1. Create a feature branch (`feat/...` or `fix/...`).
2. Run the build locally to verify docs render correctly.
3. Include concise descriptions and, if applicable, screenshots of the rendered pages.
