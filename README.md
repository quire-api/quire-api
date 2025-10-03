# Quire API

This repository contains the source used to build the **[Quire](https://quire.io) API Reference**.

## Overview

The Quire API project compiles annotated source and documentation into a browsable API reference site.

### Other Documents

* [Activity Types](docs/activity_types.md)

---

## Tools

- Generator: <https://github.com/sourcey/spectacle>

## Prerequisites

- **Node.js** and **npm** (or **pnpm/yarn**) installed
- Any additional tooling required by Spectacle (see their README)

## Build

Generate the static documentation:

```bash
./tool/build
```

## Start the Docs Website

Run a local web server for preview:

```bash
./tool/startWebsite
```

Then open the printed URL in your browser.

## Contributing

Issues and pull requests are welcome. Please:

1. Create a feature branch (`feat/...` or `fix/...`).
2. Run the build locally to verify docs render correctly.
3. Include concise descriptions and, if applicable, screenshots of the rendered pages.
