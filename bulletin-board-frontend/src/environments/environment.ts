// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  urlBase: 'http://localhost:8080',
  bulletinsPageSize: 10,
  maxImageSize: 500000,
  imageExtensions: ['image/png', 'image/jpeg'],
  imageUrl: {
    jpeg: 'data:image/jpeg;base64,',
    png: 'data:image/png;base64,',
    jpg: 'data:image/jpeg;base64,'
  },
  errorTypes: {
    SERVER: 'server',
    FORBIDDEN: 'forbidden',
    OTHER: 'other'
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
