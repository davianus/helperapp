angular.module('helperapp.services')
.factory('Fulfillment', function ($resource, baseurl) {
  return $resource(baseurl + 'user/me/fulfillments/:id', {id: '@id'});
});
