/**
 * Created by MiiKE on 14.11.2015.
 */
angular.module('starter.controllers')
.controller('NeedsCtrl',function($scope, $state, Need, $ionicModal, Tag) {

  $scope.callbackMethod = function (query) {

    var tags = Tag.query({
      q: query
    }).$promise.then(function(result) {
      var tags = result;
      if (tags.length === 0) {
        tags.push(query);
      }

      return tags;
    });

    return tags;
  }

  $ionicModal.fromTemplateUrl('templates/new-need.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.createModal = modal;
  });

  $ionicModal.fromTemplateUrl('templates/need-detail.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.detailModal = modal;
  });

  // Triggered in the login modal to close it
  $scope.closeCreate = function() {
    $scope.createModal.hide();
  };

  // Open the login modal
  $scope.create = function() {
    $scope.createModal.show();
  };
  $scope.need = {};
  // Perform the login action when the user submits the login form
  $scope.doCreate = function(need) {
    //console.log('Doing login', $scope.loginData);

    need.user = {username:window.localStorage['user']};
    need.location = {name:'Wien',location:[0,0]};
    Need.post(need,function(resp) {
      closeDetail();
      $state.go('app.needs.byme');
    });

  };

  // Triggered in the login modal to close it
  $scope.closeDetail = function() {
    $scope.detailModal.hide();
  };


  $scope.show = function(need) {
    Need.get({id:need.id},function(need){
      $scope.detailNeed = need;
      $scope.detailNeed.location = "Wien";
      $scope.detailModal.show();
    });//angular.copy(need);

  };

  $scope.newNeed = function(need) {
    $state.go('app.newNeed');
  }
  //$scope.needs = Need.query();

  $scope.editNeed = function(need) {
    $scope.need = angular.copy(need);
    $scope.need.startDate = new Date(Date.parse($scope.need.startDate));
    $scope.need.endDate = new Date(Date.parse($scope.need.endDate));
    $scope.newNeed(need);
  }

  $scope.deleteNeed = function(need) {
    var need = angular.copy(need);
    //need.user = window.localStorage.user;
    Need.delete(need, function() {
      $scope.reload();
    });
  }

})
  .controller('AllCtrl',function($scope,Need) {
    $scope.reload = function() {
      $scope.needs = Need.query({'filter': 'all'});
    }
    $scope.reload();
  }).controller('ByMeCtrl',function($scope,Need) {
  $scope.reload = function() {
    $scope.needs = Need.query({'filter': 'user', 'user': window.localStorage['user']});
  }
    $scope.reload();
  })
  .controller('ForMeCtrl',function($scope,Need) {
    $scope.reload = function() {
      $scope.needs = Need.query({'filter': 'subscriptions', 'user': window.localStorage['user']});
    }
    $scope.reload();

  })
  .controller('ToDoCtrl',function($scope,Need) {
    $scope.reload = function() {
      $scope.needs = {};
    }
    $scope.reload();
  });
