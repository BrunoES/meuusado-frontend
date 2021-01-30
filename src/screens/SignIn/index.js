import React, { useState, useContext } from 'react';
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
    ActionMessageButtonTextBold
} from '../general/styles';

import Api from '../../Api';

import TextInput from '../../components/TextInput';

//#import MULogo from '../../assets/barber.svg';
import MULogo from '../../assets/meuUsadoLogo.svg';
import EmailIcon from '../../assets/email.svg';
import LockIcon from '../../assets/lock.svg';

export default () => {
    const { dispatch: userDispatch } = useContext(UserContext);
    const navigation = useNavigation();

    const [emailField, setEmailField] = useState('');
    const [passwordField, setPasswordField] = useState('');

    const handleSignClick = async () => {
        if(emailField != '' && passwordField != '') {
            
            let json;
            try {
                json = await Api.signIn(emailField, passwordField);
            } catch(error) {
                alert('erro=' + error);
                console.log(error);
            }

            if(json == true) {
                navigation.reset({
                    routes:[{name:'MainTab'}]
                });
            } else {
                alert('E-mail e/ou senha errados!');
            }

        } else {
            alert("Preencha os campos!");
        }
    }

    const handleMessageButtonClick = () => {
        navigation.reset({
            routes: [{name: 'SignUp'}]
        });
    }

    return (
        <Container>
            <MULogo width="165%" height="264" />

            <InputArea>
                <TextInput
                    IconSvg={EmailIcon}
                    placeholder="Digite seu e-mail"
                    value={emailField}
                    onChangeText={t=>setEmailField(t)}
                />

                <TextInput
                    IconSvg={LockIcon}
                    placeholder="Digite sua senha"
                    value={passwordField}
                    onChangeText={t=>setPasswordField(t)}
                    password={true}
                />

                <CustomButton onPress={handleSignClick}>
                    <CustomButtonText>LOGIN</CustomButtonText>
                </CustomButton>
            </InputArea>

            <ActionMessageButton onPress={handleMessageButtonClick}>
                <ActionMessageButtonText>Ainda não possui uma conta?</ActionMessageButtonText>
                <ActionMessageButtonTextBold>Cadastre-se</ActionMessageButtonTextBold>
            </ActionMessageButton>

        </Container>
    );
}