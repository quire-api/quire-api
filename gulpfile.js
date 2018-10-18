var oatts = require('oatts');
var gulp = require( 'gulp' );
var del = require('del');
var mocha = require('gulp-mocha');

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
  var tests = oatts.generate('./dist/swagger.yaml', options);
  console.log(tests)
});

gulp.task('test', () =>
  gulp.src(['./generated/test/*.js'], {read: false})
    .pipe(mocha({reporter: 'list', exit: true}))
    .on('error', console.error)
);

