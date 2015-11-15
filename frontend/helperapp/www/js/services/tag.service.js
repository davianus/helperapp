angular.module('starter.controllers')
.factory('Tag', function ($resource, baseurl) {
  return $resource(baseurl + '/tags');
});
