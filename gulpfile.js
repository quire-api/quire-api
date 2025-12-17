const spectacleDoc = require('spectacle-docs');
const { series } = require('gulp');
const del = require('del');
const jsToYaml = require('js-yaml');
const fs = require('fs');
const merge = require('lodash.merge');
const swaggerYaml = './dist/swagger.yaml';

// Removes generated test artifacts (gulp v4 expects a returned promise/stream)
function clean() {
    return del(['./generated/test']);
}

// Builds merged swagger.yaml then renders docs via spectacle
function build(done) {
    const swaggerMain = jsToYaml.load(
        fs.readFileSync('./generated/swagger.yaml', 'utf8'));
    const swaggerExamples = jsToYaml.load(
        fs.readFileSync('./src/main/resources/examples.yaml', 'utf8'));
    merge(swaggerMain, swaggerExamples);

    fs.writeFileSync(swaggerYaml, jsToYaml.dump(swaggerMain, { lineWidth: -1 }));

    spectacleDoc({
        specFile: swaggerYaml,
        targetDir: './dist',
        configFile: './doc_template/config.js',
    });

    done();
}

exports.clean = clean;
exports.build = build;
exports.default = series(clean, build);

//gulp.task('test', () =>
//  gulp.src(['./generated/test/*.js'], {read: false})
//    .pipe(mocha({reporter: 'list', exit: true}))
//    .on('error', console.error)
//);

