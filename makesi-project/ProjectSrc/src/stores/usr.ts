import { createStore } from 'vuex'
import createPersistedState from "vuex-persistedstate"
import { ServerEntity } from '../tool/classDef/classDef'

function findURL(source: ServerEntity[], target: ServerEntity){
    for(let i = 0; i < source.length; i++){
        if(source[i].picture_url == target.picture_url && source[i].id == target.id)
            return i;
    }
    return -1;
}

export const store = createStore({
    state(){
        return{
            isLogin: false,
            isSuper: false,
            askIndexState: false,
            orilHisurl: <ServerEntity[]> [],
            literatureurl: <ServerEntity[]> [],
            academicUrl: <ServerEntity[]> [],
            specialUrl: <ServerEntity[]> [],
        }
    },
    mutations:{
        setAskIndexState(state, flag){
            state.askIndexState = flag;
        },
        setLogin(state, flag){
            state.isLogin = flag
        },
        setSuper(state, value){
            state.isSuper = value;
        },
        clearOriHis(state){
            while(state.orilHisurl.length != 0)
                state.orilHisurl.pop();
        },
        clearLiter(state){
            while(state.literatureurl.length != 0)
                state.literatureurl.pop();
        },
        clearAcademic(state){
            while(state.academicUrl.length != 0)
                state.academicUrl.pop();
        },
        clearSpecial(state){
            while(state.specialUrl.length != 0)
                state.specialUrl.pop();
        },
        deleteOriHisUrl(state, url){
            let indexUrl = findURL(state.orilHisurl, url);
            if(indexUrl != -1)
                state.orilHisurl.splice(indexUrl, 1);
        },
        deleteLiterUrl(state, url){
            let indexUrl = state.literatureurl.indexOf(url);
            if(indexUrl != -1)
                state.orilHisurl.splice(indexUrl, 1);
        },
        deleteAcademicUrl(state, url){
            let indexUrl = state.academicUrl.indexOf(url);
            if(indexUrl != -1)
                state.orilHisurl.splice(indexUrl, 1);
        },
        deleteSpecialUrl(state, url){
            let indexUrl = state.specialUrl.indexOf(url);
            if(indexUrl != -1)
                state.orilHisurl.splice(indexUrl, 1);
        },
        pushOriHisUrl(state, url){
            state.orilHisurl.push(url);
        },
        pushLiterUrl(state, url){
            state.literatureurl.push(url);
        },
        pushAcademicUrl(state, url){
            state.academicUrl.push(url);
        },
        pushSpecialUrl(state, url){
            state.specialUrl.push(url);
        }
    },
    getters:{
        getLogin(state){ return state.isLogin; },
        getSuper(state){ return state.isSuper; },
        getOri(state){ return state.orilHisurl; },
        getLiter(state){ return state.literatureurl; },
        getAcademic(state){ return state.academicUrl; },
        getSpecial(state){ return state.specialUrl; },
        getAskIndexState(state){ return state.askIndexState; },
    },
    actions:{
        setLoginA(context, flag){
            context.commit('setLogin', flag);
        },
        setSuperA(context, value){
            context.commit('setSuper', value);
        }, 
    },
    plugins: [createPersistedState(
        {storage: window.sessionStorage}
    )],
})