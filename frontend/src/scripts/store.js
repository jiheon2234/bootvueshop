import {createStore} from 'vuex'

// Create a new store instance.
const store = createStore({
    state() {
        return {
            accounts: {
                id: 0,
            }
        }
    },
    mutations: {
        setAccount(state, payload) {
            state.accounts.id = payload;
        }
    }
})

export default store;