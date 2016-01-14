angular.module('helperapp.services')
.factory('Fulfillment', function ($resource, baseurl) {
  return $resource(baseurl + 'user/:user/fulfillments/:id', {id: '@id'});
});
