angular.module('helperapp.services')
.factory('Subscription', function ($resource, baseurl) {
    return $resource(baseurl + 'user/me/subscriptions/:id', {id: '@id'});
  });
