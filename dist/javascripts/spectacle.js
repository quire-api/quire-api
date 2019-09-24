var stCurrentUser = "curusr",
	isUser = false;

function _showHeaderIcon() {
	$('#header-icons').removeClass('hide');
}

function _updateLoginUser(response) {
	var user = $('.t-user'),
		userId = response['id'],
		userName = response['name'],
		initials = response['initials'],
		userImg = response['image'],
		iconColor = response['iconColor'];

	isUser = true;

	// update for icon and its dropdown
	user.find('.dropdown-header').text(userName);
	user.find('.user-icon-wrapper').append(
		userImg != '' ?
			$('<img class="img-icon x30 circle" src="' + userImg + '" alt="' + userName + '">') :
			$('<div class="img-icon x30 circle bg iconc-' + iconColor +'">' + initials + '</div>')
	);
	user.find('.icon-user-o').parent().attr('href', '/u/' + userId);

    $('#header-wrapper').addClass('qr-collapse-navbar-768');
	// remove stuff for non-user
	$('li.login').remove();

	$('.workspace').removeClass('hide');

	user.removeClass('hide');
	_showHeaderIcon();
}

function _updateNonLoginUser() {
	// remove stuff for user
	$('#header-wrapper').addClass('qr-collapse-navbar');
    $('li.workspace, li.t-user').remove();

    $('.login').removeClass('hide');

	_showHeaderIcon();
}

window.sendQS = function (data, callback, failCallback) {
	var channel = $('body').data('qs') || 'qs';
		//#7994: for chrome extension, we have to use /qs
//	var segs = location.pathname.match(/^\/([^/]+)\//);

	return $.ajax({
		type: 'POST',
		url: '/' + channel,
			//for chrome extension, we need to specify absolute path
		data: JSON.stringify(data),
		contentType: "application/json;charset=UTF-8",
//		headers: {"x-spg": segs ? segs[1]: ''}
	})
	.done(callback)
	.fail(function (xhr) {
		if (failCallback)
			failCallback(xhr);
		else {
			var msg = netErrorMsg(xhr);
			if (msg)
				showAlert($('#body'), msg, 'alert-danger');
		}

	});
};

$(function() {
  // $(document).foundation();

  var $sidebar = $('#sidebar');
  if ($sidebar.length) {
    var $docs = $('#docs');
    var $nav = $sidebar.find('nav');

    //
    // Setup sidebar navigation
    var traverse = new Traverse($nav, {
      threshold: 10,
      barOffset: $sidebar.position().top
    });

    $nav.on('update.traverse', function(event, element) {
      $nav.find('section').removeClass('expand');
      var $section = element.parents('section:first');
      if ($section.length) {
        $section.addClass('expand');
      }
    });

    //
    // Bind the drawer layout
    var $drawerLayout = $('.drawer-layout'),
      $drawer = $drawerLayout.find('.drawer'),
      closeDrawer = function() {
        $drawer.addClass('sliding');
        $drawer.removeClass('slide-right slide-left');
        $drawer.find('.drawer-overlay').remove();
        $drawerLayout.removeClass('drawer-open drawer-slide-left-large');

        return false;
      };

    $drawer.on('transitionend webkitTransitionEnd oTransitionEnd', function () {
        $drawer.removeClass('sliding');
    });

    // Drawer open buttons
    $drawerLayout.find('[data-drawer-slide]').click(function(e) {
      var $this = $(this),
        direction = $this.data('drawer-slide');
      $drawerLayout.addClass('drawer-slide-right-large');
      $drawer.addClass('slide-' + direction);
      $drawer.addClass('sliding');

      var $overlay = $('<a href="#" class="drawer-overlay"></a>')
      $drawer.append($overlay);
      $overlay.click(closeDrawer);

      return false;
    });

    // Drawer close buttons
    $drawerLayout.find('[data-drawer-close]').click(closeDrawer);
  }

  sendQS([stCurrentUser, null], function(response) {
    if (response)
      _updateLoginUser(response);
    else
      _updateNonLoginUser();
  }, function() {
    _updateNonLoginUser();
    _showHeaderIcon();
  });
});

/**
 * Creates a new instance of Traverse.
 * @class
 * @fires Traverse#init
 * @param {Object} element - jQuery object to add the trigger to.
 * @param {Object} options - Overrides to the default plugin settings.
 */
function Traverse(element, options) {
  this.$element = element;
  this.options  = $.extend({}, Traverse.defaults, this.$element.data(), options);

  this._init();
}

/**
 * Default settings for plugin
 */
Traverse.defaults = {
  /**
   * Amount of time, in ms, the animated scrolling should take between locations.
   * @option
   * @example 500
   */
  animationDuration: 500,
  /**
   * Animation style to use when scrolling between locations.
   * @option
   * @example 'ease-in-out'
   */
  animationEasing: 'linear',
  /**
   * Number of pixels to use as a marker for location changes.
   * @option
   * @example 50
   */
  threshold: 50,
  /**
   * Class applied to the active locations link on the traverse container.
   * @option
   * @example 'active'
   */
  activeClass: 'active',
  /**
   * Allows the script to manipulate the url of the current page, and if supported, alter the history.
   * @option
   * @example true
   */
  deepLinking: false,
  /**
   * Number of pixels to offset the scroll of the page on item click if using a sticky nav bar.
   * @option
   * @example 25
   */
  barOffset: 0
};

/**
 * Initializes the Traverse plugin and calls functions to get equalizer functioning on load.
 * @private
 */
Traverse.prototype._init = function() {
  var id = this.$element[0].id, // || Foundation.GetYoDigits(6, 'traverse'),
      _this = this;
  this.$targets = $('.introduction .doc-copy > h1, [data-traverse-target]');
  this.$links = this.$element.find('a');
  this.$element.attr({
    'data-resize': id,
    'data-scroll': id,
    'id': id
  });
  this.$active = $();
  this.scrollPos = parseInt(window.pageYOffset, 10);

  this._events();
};

/**
 * Calculates an array of pixel values that are the demarcation lines between locations on the page.
 * Can be invoked if new elements are added or the size of a location changes.
 * @function
 */
Traverse.prototype.calcPoints = function(){
  var _this = this,
      body = document.body,
      html = document.documentElement;

  this.points = [];
  this.winHeight = Math.round(Math.max(window.innerHeight, html.clientHeight));
  this.docHeight = Math.round(Math.max(body.scrollHeight, body.offsetHeight, html.clientHeight, html.scrollHeight, html.offsetHeight));

  this.$targets.each(function(){
    var $tar = $(this),
        pt = $tar.offset().top; // Math.round($tar.offset().top - _this.options.threshold);
    $tar.targetPoint = pt;
    _this.points.push(pt);
  });
};

/**
 * Initializes events for Traverse.
 * @private
 */
Traverse.prototype._events = function() {
  var _this = this,
      $body = $('html, body'),
      opts = {
        duration: _this.options.animationDuration,
        easing:   _this.options.animationEasing
      };

  $(window).one('load', function(){
    _this.calcPoints();
    _this._updateActive();

    var timer;
    $(this).resize(function(e) {
      _this.reflow();
    }).scroll(function(e) {
        if (timer)
            clearTimeout(timer);

        timer = setTimeout(function() {
            _this._updateActive();
        }, 100);
    });
  })

  this.$element.on('click', 'a[href^="#"]', function(e) { //'click.zf.traverse'
      e.preventDefault();
      var arrival   = this.getAttribute('href').replace(/\./g, "\\."),
          scrollPos = $(arrival).offset().top - _this.options.barOffset; // - _this.options.threshold / 2 - _this.options.barOffset;

      $body.stop(true).animate({
        scrollTop: scrollPos
      }, opts);
//       $body.scrollTop(scrollPos);
    });
};

/**
 * Calls necessary functions to update Traverse upon DOM change
 * @function
 */
Traverse.prototype.reflow = function(){
  this.calcPoints();
  this._updateActive();
};

/**
 * Updates the visibility of an active location link,
 * and updates the url hash for the page, if deepLinking enabled.
 * @private
 * @function
 * @fires Traverse#update
 */
 Traverse.prototype._updateActive = function(){
   var winPos = parseInt(window.pageYOffset, 10),
       curIdx;

   if(winPos + this.winHeight === this.docHeight){ curIdx = this.points.length - 1; }
   else if(winPos < this.points[0]){ curIdx = 0; }
   else{
     var isDown = this.scrollPos < winPos,
         _this = this,
         curVisible = this.points.filter(function(p, i){
           return isDown ?
             p <= (winPos + _this.options.barOffset + _this.options.threshold) :
             (p - (_this.options.barOffset + _this.options.threshold)) <= winPos;
            //   p <= (winPos - (offset - _this.options.threshold)) :
            //   (p - (-offset + _this.options.threshold)) <= winPos;
         });
     curIdx = curVisible.length ? curVisible.length - 1 : 0;
   }

   var $prev = this.$active;
   var $next = this.$links.eq(curIdx);
   this.$active.removeClass(this.options.activeClass);
   this.$active = $next.addClass(this.options.activeClass);

   if(this.options.deepLinking){
     var hash = this.$active[0].getAttribute('href');
     if(window.history.pushState){
       window.history.pushState(null, null, hash);
     }else{
       window.location.hash = hash;
     }
   }

   this.scrollPos = winPos;

   // Fire event if the active element was changed
   var changed = $prev[0] !== $next[0];
   if (changed) {
     this.$element.trigger('update.traverse', [this.$active]);
   }
 };
