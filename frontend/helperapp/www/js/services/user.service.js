angular.module('helperapp.services')
.factory('User', function ($resource, baseurl) {
  return $resource(baseurl + 'user/:username', {username:'@username'}, {
    'query': { method: 'GET', isArray: true},
    'get': {
      method: 'GET',
    },
    'update': { method:'PUT' },
    'post': { method: 'POST',  transformResponse: []},
    'login': { method: 'POST', params: {username:"me"}}
  });
});
