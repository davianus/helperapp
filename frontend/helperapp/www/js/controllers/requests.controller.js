angular.module('starter.controllers')

.controller('RequestCtrl',function($scope) {
  $scope.requests = [
    {id: 1, title: "100 Sessel"},
    {id: 2, title: "Babynahrung"}
  ]
});
