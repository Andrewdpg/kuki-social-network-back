/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/main/resources/templates/**/*.html',
    './src/main/resources/static/**/*.js'
  ],
  theme: {
    extend: {
      backgroundImage: {
        'gradient-custom': 'linear-gradient(135deg, #fff0e6 0%, #fff9e6 100%)',
      }
    },
  },
  plugins: [],
}

