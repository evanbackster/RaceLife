module.exports = function(grunt) {

  // Project configuration.
  grunt.initConfig({

    pkg: grunt.file.readJSON('package.json'),

    uglify: {
      build: {
        src: 'js/global.js',
        dest: 'js/build/global.min.js'
      }
    },

    sass: {
      dist: {                            // Target
        options: {                       // Target options
          style: 'compressed'
        },
        files: {                         // Dictionary of files
          'css/global.css': 'scss/global.scss',       // 'destination': 'source'
          'css/start-stop.css': 'scss/start-stop.scss'       // 'destination': 'source'
        }
      }
    },

    watch: {

        css: {
            files: ['scss/*.scss', 'scss/partial/*.scss'],
            tasks: ['sass'],
            options: {
                spawn: false,
                livereload: true
            }
        } 
    }

  });

  // Load the plugin that provides the "uglify" task.
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-sass');
  grunt.loadNpmTasks('grunt-contrib-watch');

  // Default task(s).
  grunt.registerTask('default', ['uglify', 'sass']);

  grunt.registerTask('dev', ['watch']);

};