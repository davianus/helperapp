/**
 * Created by MiiKE on 14.11.2015.
 */
angular.module('helperapp.controllers')
.controller('NeedsCtrl',function($scope, $state, $http, Need, $ionicModal, Tag, Fulfillment, $filter) {



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

  $scope.create = function(need) {
    $scope.need = need || new Need();
    $scope.canbeSaved = false;
    $scope.createModal.show();
  };

  $scope.doCreate = function() {
    $scope.need.$save().then(
        function() {
            $scope.closeCreate();
            $state.go('app.needs.byme',{},{reload:true});
        }
    );
  };

  $scope.closeDetail = function() {
    $scope.detailModal.hide();
  }

  $scope.show = function(need) {
    Need.get({id:need.id},function(need){
      $scope.detailNeed = need;
      $scope.notMyNeed = window.localStorage['username'] !== $scope.detailNeed.user.username;
      $scope.detailModal.show();
    });

  };

  $scope.fulfillment = new Fulfillment();
  $scope.doFulfillment = function() {
    $scope.fulfillment.requestId = $scope.detailNeed.id;
    $scope.fulfillment.until = $filter('date')($scope.fulfillment.untilDate, 'yyyy-MM-dd');
    Fulfillment.save($scope.fulfillment, function() {
      $state.go('app.needs.todo');
      $scope.closeDetail();
      $scope.fulfillment = new Fulfillment();
    });
  };

  $scope.editNeed = function(need) {
    $scope.need = angular.copy(need);
    $scope.need.startDate = new Date(Date.parse($scope.need.startDate));
    $scope.need.endDate = new Date(Date.parse($scope.need.endDate));
    $scope.create(need);
  };

  $scope.deleteNeed = function(need) {
    var need = angular.copy(need);
    Need.delete(need, function() {
      $scope.reload();
    });
  };

  $scope.doneFulfillment = function(fulfillment) {
    var tmp = {};
    tmp.id = fulfillment.id;
    tmp.done = true;
    Fulfillment.save(tmp, function() {
      $scope.detailNeed = Need.get({id: $scope.detailNeed.id});
    });
  };

  $scope.getLatLng = function() {

      var username = window.localStorage['username'];
      var password = window.localStorage['password'];
      window.localStorage.removeItem('username');
      window.localStorage.removeItem('password');

    $http.get('https://maps.googleapis.com/maps/api/geocode/json?address=' + encodeURIComponent($scope.need.location.name)).then(
        function(response) {
            window.localStorage['username'] = username;
            window.localStorage['password'] = password;

            $scope.need.location.location = [
                response.data.results[0].geometry.location.lat,
                response.data.results[0].geometry.location.lng
            ];

            $scope.canBeSaved = true;
        }
    );
  };

})
  .controller('AllCtrl',function($scope,Need) {
    $scope.showCreate = false;
    $scope.reload = function() {
      $scope.needs = Need.query({'filter': 'all'});
      $scope.fulfillment=[];
    }
    $scope.$on('$ionicView.beforeEnter', function() {
      $scope.reload();
    });
  }).controller('ByMeCtrl',function($scope,Need) {
    $scope.showCreate = true;
  $scope.reload = function() {
    $scope.needs = Need.query({'filter': 'user');
    $scope.fulfillment=[];
  }
    $scope.$on('$ionicView.beforeEnter', function() {
      $scope.reload();
    });
  })
  .controller('ForMeCtrl',function($scope,Need) {
    $scope.showCreate = false;
    $scope.reload = function() {
      $scope.needs = Need.query({'filter': 'subscriptions'});
      $scope.fulfillment=[];
    }
    $scope.$on('$ionicView.beforeEnter', function() {
      $scope.reload();
    });
  })
  .controller('ToDoCtrl',function($scope, Fulfillment) {
    $scope.showCreate = false;
    $scope.reload = function() {
      Fulfillment.query(function(fulfillments) {
        $scope.needs = [];
        $scope.fulfillments = fulfillments;
      });
    }
    $scope.$on('$ionicView.beforeEnter', function() {
      $scope.reload();
    });
  });
