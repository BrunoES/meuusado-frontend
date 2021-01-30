import React, { useState, useContext } from 'react';
import { Text } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import AsyncStorage from '@react-native-community/async-storage';

import { UserContext } from '../../contexts/UserContext';
import {
    Container,
    InputArea,
    CustomButton,
    CustomButtonText,
    ActionMessageButton,
    ActionMessageButtonText,
    ActionMessageButtonTextBold,
    Logo
} from '../general/styles';

import TextInput from '../../components/TextInput';

import Api from '../../Api';

import MULogo from '../../assets/meuUsadoLogo.svg';
import PersonIcon from '../../assets/person.svg';
import EmailIcon from '../../assets/email.svg';
import LockIcon from '../../assets/lock.svg';

export default () => {
    const { dispatch: userDispatch } = useContext(UserContext);
    const navigation = useNavigation();

    const [tituloField, setTituloField] = useState('');
    const [descricaoField, setDescricaoField] = useState('');
    const [marcaField, setMarcaField] = useState('');
    const [modeloField, setModeloField] = useState('');
    const [valorField, setValorField] = useState('');

    const handleCadastroAnuncioClick = async () => {
        if(tituloField != '' && descricaoField != '' && marcaField != '' && modeloField != '' && valorField != '') {
            
            let res;
            try {
                res = await Api.cadastroAnuncio(tituloField, descricaoField, modeloField, valorField);
            } catch(error) {
                console.log(error);
            }
            
            console.dir(res);
            /*
            if(res.token) {
                await AsyncStorage.setItem('token', res.token);

                userDispatch({
                    type: 'setAvatar',
                    payload:{
                        avatar: res.data.avatar
                    }
                });

                navigation.reset({
                    routes:[{name:'MainTab'}]
                });

            } else {
                alert("Erro: "+res.error);
            }
            */
        } else {
            alert("Preencha os campos");
        }
    }

    const handleMessageButtonClick = () => {
        navigation.reset({
            routes: [{name: 'SignIn'}]
        });
    }

    return (
        <Container>
            <MULogo className={Logo} width="165%" height="264" />

            <InputArea>
                <TextInput
                    IconSvg={PersonIcon}
                    placeholder="Título"
                    value={tituloField}
                    onChangeText={t=>setTituloField(t)}
                />

                <TextInput
                    IconSvg={EmailIcon}
                    placeholder="Descrição"
                    value={descricaoField}
                    onChangeText={t=>setDescricaoField(t)}
                />

                <TextInput
                    IconSvg={PersonIcon}
                    placeholder="Marca"
                    value={marcaField}
                    onChangeText={t=>setMarcaField(t)}
                />
                
                <TextInput
                    IconSvg={PersonIcon}
                    placeholder="Modelo"
                    value={modeloField}
                    onChangeText={t=>setModeloField(t)}
                />

                <TextInput
                    IconSvg={LockIcon}
                    placeholder="Valor do veículo"
                    value={valorField}
                    onChangeText={t=>setValorField(t)}
                />

                <CustomButton onPress={handleCadastroAnuncioClick}>
                    <CustomButtonText>CADASTRAR</CustomButtonText>
                </CustomButton>
            </InputArea>

            <ActionMessageButton onPress={handleMessageButtonClick}>
                <ActionMessageButtonText>Já possui uma conta?</ActionMessageButtonText>
                <ActionMessageButtonTextBold>Faça Login</ActionMessageButtonTextBold>
            </ActionMessageButton>

        </Container>
    );
}