import axios from "axios";
import { GET_ERRORS } from "./types";

export const registerNewUser = (newUser, history) => async (dispatch) => {
  try {
    await axios.post("http://localhost:8080/api/users/register", newUser);

    history.push("/login");

    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};
