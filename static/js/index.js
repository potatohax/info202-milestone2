// create the Vue controller
const app = Vue.createApp();



// import data store
import { dataStore } from './data-store.js'
        app.use(dataStore);


// import navigation  menu component
import { navigationMenu } from './navigation-menu.js';
app.component('navmenu', navigationMenu);

// attach the controller to the <main> tag
app.mount("main");