angular.module('starter.controllers')
.factory('Subscription', function ($resource, baseurl) {
  return $resource(baseurl + '/user/:user/subscriptions/:id', {id: '@id'});
});
