angular.module('starter.controllers')

.controller('SubscriptionCtrl', function($scope) {
  $scope.subscriptions = [
    {
      tags: [
        'Essen',
        'Apfel'
      ]
    },
    {
      tags: [
        'Möbel',
        'Sessel'
      ]
    }
  ];
});
