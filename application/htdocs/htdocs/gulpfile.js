var gulp = require('gulp');

var uglify = require('gulp-uglify');
var concat = require('gulp-concat');

/*var sass = require('gulp-sass');

gulp.task('scss', function () {

    return gulp.src('css/*.scss')
    .pipe(sass({outputStyle: 'compressed'}).on('error', sass.logError))
    .pipe(gulp.dest('css'));

});*/

var uglifyOptions = {
	mangle: {
		reserved: ['require', 'exports', 'module','$','jQuery']
	},

	'output':{
		// http://lisperator.net/uglifyjs/codegen
		ascii_only:true, // output ASCII-safe? (encodes Unicode characters as ASCII)
		//quote_keys:true, // quote all keys in object literals?
		//bracketize:true // use brackets every time
	},
	compress:{
		// http://lisperator.net/uglifyjs/compress
		properties: false // optimize property access: a["foo"] → a.foo
		
	},
	ie8:true
};

gulp.task('concat', function() {
  return gulp.src(['src/js/libs/bootstrap.js','src/js/libs/bootbox.js', 'src/js/libs/jquery-validate.js', 'src/js/libs/jquery-cookie.js', 'src/js/libs/oneplace.js','src/js/libs/jquery.toastmessage.js','src/js/footer.js'])
    .pipe(concat('common.js'))
    .pipe(gulp.dest('src/js'));
});

gulp.task('compress',function(){
	return gulp.src(['src/**/*.js','!uikit.js','!jquery.js','!sea.js'])
		.pipe(uglify({
				mangle: {
					except: ['require', 'exports', 'module']
				},

				'output':{
					quote_keys:true,
					ascii_only:true
				}
			}).on('error',function(fileName,lineNumber,message){
				console.log(fileName);
				console.log(lineNumber);
                console.log(message);
			}))
		.pipe(gulp.dest('js'));
});


gulp.task('default', ['concat', 'compress']);
gulp.task('cp', ['compress']);
