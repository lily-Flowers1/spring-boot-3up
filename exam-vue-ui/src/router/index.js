import {createRouter, createWebHistory} from "vue-router";
import {unauthorized} from "@/net";

const router = createRouter(
    {
        history:createWebHistory(import.meta.env.BASE_URL),
        routes:[
            {
                path:"/",
                name:"welcome",
                component:()=>import('@/views/WelcomeView.vue'),
                children:[
                    {
                        path: '',
                        name: 'welcome-login',
                        component:()=>import('@/views/welcome/LoginPage.vue')
                    }
                ]
            },
            {
                path: '/index',
                name: 'index',
                component:()=>import('@/views/indexView.vue')
            }
        ]
    })
router.beforeEach((to,from,next)=>{
    const isUnauthorized = unauthorized()
    if (!isUnauthorized&&to.name.startsWith('welcome-')){
        next('/index')
    }else if (isUnauthorized&&to.fullPath.startsWith('/index')){
        next('/')
    }else next()

})
export default router