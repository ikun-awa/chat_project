const path = require('path');

module.exports = {
  entry: {
    app: './js/xiao_guo.js',
  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    clean: true,
    filename: './js/xiao_guo.js',
  },
};
