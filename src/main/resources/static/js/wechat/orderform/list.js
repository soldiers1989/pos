require(['jquery', 'vue', 'utils', 'weui', 'messager'], function ($, Vue, utils, weui, messager) {
    var vue = new Vue({
        el: '#content',
        data: {
            member: {},
            tabs: [
                {
                    id: 'all',
                    text: '全部'
                }, {
                    id: 'un_pay',
                    text: '待支付'
                }, {
                    id: 'payed',
                    text: '待发货'
                }, {
                    id: 'delivered',
                    text: '已发货'
                }, {
                    id: 'received',
                    text: '已完成'
                }
            ],
            orderForms: [],
            activeId: utils.getQueryString('page')? utils.getQueryString('page'): 'all',
            loading: true,
            orderStatus: []
        },
        filters: {
            coverPath: function (val) {
                return utils.patchUrl('/attachment/download/' + val);
            },
            price: function (val) {
                return utils.formatMoney(val);
            },
            date: function (val) {
                return new Date(val).format("yyyy-MM-dd HH:mm:ss");
            },
            status: function (val) {
                var result = '';
                $.each(vue.orderStatus, function () {
                    if(this.id.toUpperCase() === val) {
                        result = this.text;
                    }
                });
                return result;
            }
        },
        methods: {
            loadOrderForm: function (status) {
                var self = this;
                $.ajax({
                    url: utils.patchUrl('/api/orderForm'),
                    data: $.extend({
                        sort: 'updatedDate',
                        order: 'desc',
                        logicallyDeleted:false,
                        'member.id': this.member.id,
                        logicallyDeleted: false
                    }, status? {status: status}: {}),
                    success: function(data) {
                        self.orderForms = data.content;
                        self.loading = false;
                    }
                })
            },
            getTotal: function (orderItems) {
                var total = 0;
                $.each(orderItems, function () {
                    total += this.product.price * this.count;
                });
                return total;
            },
            getAmount: function (orderItems) {
                var amount = 0;
                $.each(orderItems, function () {
                    amount += this.count;
                });
                return amount;
            },
            tabClick: function (id) {
                if(id === 'all') {
                    this.loadOrderForm();
                } else {
                    this.loadOrderForm(id.toUpperCase());
                }
            },
            orderFormDetail: function (orderForm) {
                window.location.href = utils.patchUrlPrefixUrl('/wechat/orderform/detail?id=' + orderForm.id);
            },
            pay: function (orderForm) {
                window.location.href = utils.patchUrlPrefixUrl('/wechat/orderform/un_pay?id=' + orderForm.id);
            },
            remove: function (orderForm) {
                messager.alert('是否确认删除？', function () {
                    $.ajax({
                        url: utils.patchUrl('/api/orderForm/' + orderForm.id),
                        contentType: 'application/json',
                        type: 'DELETE',
                        success: function() {
                            messager.bubble("操作成功");
                            setTimeout(function () {
                                window.location.reload();
                            }, 500)
                        }
                    })
                })
            },
            receive: function (orderForm) {
                messager.alert('是否确认收货？', function () {
                    $.ajax({
                        url: utils.patchUrl('/api/orderForm/receive/' + orderForm.id),
                        contentType: 'application/json',
                        type: 'POST',
                        success: function() {
                            messager.bubble("操作成功");
                            setTimeout(function () {
                                window.location.reload();
                            }, 500)
                        }
                    })
                })
            },
            applyReject: function (orderForm) {
                window.location.href = utils.patchUrlPrefixUrl('/wechat/orderform/applyReject?id=' + orderForm.id);
            }
        },
        mounted: function () {
            var self = this;
            var username, token;
            token = utils.getQueryString("token");
            username = utils.getQueryString("username");
            if(token && username) {
                $.ajax({
                    url: utils.patchUrlPrefixUrl('/token/login'),
                    data: {
                        appId: 'tonr',
                        appSecret: 'secret',
                        username: username,
                        token: token
                    },
                    type: 'POST',
                    success: function(data) {
                        window.localStorage.accessToken = data['access_token'];
                        window.localStorage.refreshToken = data['refresh_token'];
                        window.localStorage.expiration = new Date().getTime() + ((data['expires_in'] / 2) * 1000);
                        utils.getLoginMember(function (member) {
                            self.member = member;
                            self.tabClick(self.activeId);
                        }, true);
                        $.ajax({
                            url: utils.patchUrl('/api/orderForm/status'),
                            success: function (data) {
                                self.orderStatus = data;
                            }
                        })
                    }
                })
            } else {
                utils.getLoginMember(function (member) {
                    self.member = member;
                    self.tabClick(self.activeId);
                }, true);
            }
        }
    });
});
