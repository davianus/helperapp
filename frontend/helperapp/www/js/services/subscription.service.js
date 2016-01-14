angular.module('helperapp.services')
.factory('Subscription', function ($resource, baseurl) {
    return $resource(baseurl + 'user/:user/subscriptions/:id', {id: '@id',user:'@user'});
  });
