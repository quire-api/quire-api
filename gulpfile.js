var oatts = require('oatts');
var gulp = require( 'gulp' );
var del = require('del');
var mocha = require('gulp-mocha');
var jsToYaml = require('js-yaml');
var fs = require('fs');
var merge = require('lodash.merge');
var swaggerYaml = './dist/swagger.yaml';

var options = {
    "scheme": "http",
    "host": "localhost:8080",
    "writeTo": "./generated/test",
    "customValuesFile": "./src/main/resources/test_template.json"
};

gulp.task('clean', function(cb){
  del(['./generated/test'], cb);
});

gulp.task('build', function () {
    var swaggerMain = jsToYaml.safeLoad(
        fs.readFileSync('./generated/swagger.yaml', 'utf8'));
    var swaggerExamples = jsToYaml.safeLoad(
        fs.readFileSync('./src/main/resources/examples.yaml', 'utf8'));
    merge(swaggerMain, swaggerExamples);

   fs.writeFileSync(swaggerYaml, jsToYaml.safeDump(swaggerMain))

//  var tests = oatts.generate(swaggerYaml, options);
//  console.log(tests)
});

gulp.task('test', () =>
  gulp.src(['./generated/test/*.js'], {read: false})
    .pipe(mocha({reporter: 'list', exit: true}))
    .on('error', console.error)
);

