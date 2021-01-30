import AsyncStorage from "@react-native-community/async-storage";

const BASE_API = 'http://10.0.0.121:8082/api';

export default {
    checkToken: async (token) => {
        const req = await fetch(`${$BASE_API}/auth/refresh`, {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({token})
        });
        const json = await req.json();
        return json;
    },
    signIn: async(email, password) => {
        const req = await fetch(`${BASE_API}/v1/login`, {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({email, password})
        });
        const json = await req.json();
        return json;
    },
    signUp: async(name, email, password) => {
        const req = await fetch(`${BASE_API}/v1/usuario`, {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({name, email, password})
        });
        const json = await req.json();
        return json;
    },
    cadastroAnuncio: async(titulo, descricao, idModelo, valor) => {
        const req = await fetch(`${BASE_API}/v1/anuncio`, {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({titulo, descricao, idModelo, valor})
        });
        const json = await req.json();
        return json;
    },
    getBarbers: async (lat = null, lng = null, address = null) => {
        const token = await AsyncStorage.getItem('token');

        console.log("LAT", lat);
        console.log("LNG", lng);
        console.log("ADDRESS", address);

        const req = await fetch(`${BASE_API}/barbers?token=${token}&lat=${lat}&lng=${lng}&address=${address}`);
        const json = await req.json();

        return json;
    }
}