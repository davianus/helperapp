angular.module('helperapp.services')
.factory('Tag', function ($resource, baseurl) {
  return $resource(baseurl + '/tags');
});
