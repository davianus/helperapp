angular.module('starter.controllers')
.factory('Need', function ($resource, baseurl) {
  return $resource(baseurl + 'requests/:id', {}, {
    'query': { method: 'GET', isArray: true},
    'get': {
      method: 'GET',

      /*,
      transformResponse: function (data) {
        data = angular.fromJson(data);
        return data;
      }*/
    },
    'update': { method:'PUT' },
    'post': { method: 'POST'}
  });
});
