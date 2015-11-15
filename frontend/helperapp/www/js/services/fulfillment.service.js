angular.module('starter.controllers')
.factory('Fulfillment', function ($resource, baseurl) {
  return $resource(baseurl + 'user/:user/fulfillments/:id', {id: '@id', user: '@user'});
});
