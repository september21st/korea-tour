import axios from 'axios';
import { URL } from '_utils/api';
import { SIGN_IN, SIGN_OUT, HOST_APPLY } from './types';

export function signIn(APIdata) {
  let url = `${URL}/signin`;
  return function (dispatch) {
    axios
      .post(url, APIdata)
      .then(response => {
        const userData = response.data;
        return dispatch({
          type: SIGN_IN,
          APIdata,
          userData,
        });
      })
      .catch(response =>
        dispatch({
          type: SIGN_IN,
          error: response.error,
        })
      );
  };
}

export function signOut() {
  return {
    type: SIGN_OUT,
  };
}
export function hostApply() {
  return {
    type: HOST_APPLY,
  };
}
