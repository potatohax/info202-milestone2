/* global Vue, axios */
var productsApi = "/api/products";
var categoryApi = "/api/categories";
var categoriesFilterApi = ({category}) => `/api/categories/${category}`;

const app = Vue.createApp({

    data() {
        return {
            // models map (comma separated key/value pairs)
            products: new Array(),
            categories: new Array()

        };
    },

    mounted() {
        // semicolon separated statements
        this.getProducts();
        this.getCategories();


    },

    methods: {
        // comma separated function declarations
        getProducts() {

            // send GET request
            axios.get(productsApi)
                    .then(response => {
                        // store response in students model
                        this.products = response.data;
                    })
                    .catch(error => {
                        console.error(error);
                        alert("An error occurred - check the console for details.");
                    });

        },
        getCategories() {
            // send GET request
            axios.get(categoryApi)
                    .then(response => {
                        // store response in students model
                        this.categories = response.data;
                    })
                    .catch(error => {
                        console.error(error);
                        alert("An error occurred - check the console for details.");
                    });

        },
        // click handler for the major filter buttons
        filterByCategory(category) {
            axios.get(categoriesFilterApi({'category': category}))
                    .then(response => {
                        this.products = response.data;
                    })
                    .catch(error => {
                        console.error(error);
                        alert("An error occurred - check the console for details.");
                    });
        },
        buyProduct(product) {
            dataStore.commit("selectProduct", product);
            window.location = "quantity.html";
        }

    },

    // other modules
    mixins: [NumberFormatter]

});

// other component imports go here

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