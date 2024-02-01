var salesApi = "/api/sales";
"use strict";
class SaleItem {
    constructor(product, quantityPurchased) {
        this.product = product;
        this.quantityPurchased = quantityPurchased;
        this.salePrice = product.listPrice;
    }
}

class Sale {
    constructor(customer, items) {
        this.customer = customer;
        this.items = items;
    }
}

const app = Vue.createApp({

    data() {
        return {
// models (comma separated key/value pairs)
            quantity: 1,
            totalPrice: 0
        };
    },
    computed: Vuex.mapState({
        product: 'selectedProduct',
        items: 'items',
        customer: 'customer'
    }),
    mounted() {
// semicolon separated statements


    },
    methods: {
        // comma separated function declarations
        addToCart() {
            var productStock = this.product.quantityInStock;
            console.log(productStock);
            if (this.quantity > productStock) {
                alert("The quantity inputted exceeds this product's stock availability")

            } else if(this.quantity <= 0) {
                alert("Inappropriate quantity selected");
            }else {
                dataStore.commit("addItem", new SaleItem(this.product, this.quantity));
                window.location = "view-products.html";
            }

        },
        getItemTotal(item) {
            var price = item.quantityPurchased * item.salePrice;
            return price;
        },
        getTotalPrice() {
            var totalPrice = 0;
            for (var item of this.items) {
                var price = this.getItemTotal(item);
                totalPrice += price;
            }
            this.totalPrice = totalPrice;
        },
        checkOut() {
            let sale = new Sale(this.customer, this.items);
            axios.post(salesApi, sale)
                    .then(() => {
                        dataStore.commit("clearItems");
                        window.location = 'order-confirmation.html';
                    })
                    .catch(error => {
                        alert(error.response.data.message);
                    });
        },
        checkAvailability() {
        }

    },
    // other modules
    mixins: [NumberFormatter]

});
/* other component imports go here */

// import data store
import { dataStore } from './data-store.js'
        app.use(dataStore);
// import the navigation menu
import { navigationMenu } from './navigation-menu.js';
import { NumberFormatter } from './number-formatter.js';
// register the navigation menu under the <navmenu> tag
app.component('navmenu', navigationMenu);
// attach the controller to the <main> tag
app.mount("main");