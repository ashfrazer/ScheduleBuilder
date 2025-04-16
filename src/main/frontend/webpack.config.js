// ScheduleBuilder/src/main/frontend/webpack.config.js
const path = require('path');
const pkg  = require('./package.json');

module.exports = {
    mode: process.env.NODE_ENV || 'development',

    // now __dirname is .../frontend
    entry: path.resolve(__dirname, 'src', 'index.js'),

    output: {
        // this drops your bundle into ../resources/static/components.js
        path: path.resolve(__dirname, '../resources/static'),
        filename: pkg.main,
        library: {
            name: 'components',
            type: 'umd',
            umdNamedDefine: true
        },
    },

    resolve: {
        extensions: ['.js', '.jsx']
    },

    module: {
        rules: [
            {
                test: /\.m?jsx?$/,
                exclude: /node_modules/,
                use: 'babel-loader'
            },
            {
                test: /\.css$/,
                use: ['style-loader','css-loader']
            }
        ]
    },

    externals: {
        react:      'React',
        'react-dom': 'ReactDOM'
    }
};
