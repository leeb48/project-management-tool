import React, { useState } from "react";
import { connect } from "react-redux";
import { registerNewUser } from "../../actions/authActions";

const Register = () => {
  const [fVal, setFVal] = useState({
    fullName: "",
    username: "",
    password: "",
    confirmPassword: "",
  });

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) =>
    setFVal({ ...fVal, [e.target.name]: e.target.value });

  const onSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    registerNewUser(fVal);
  };

  const { fullName, username, password, password2 } = fVal;

  return (
    <div className="register">
      <div className="container">
        <div className="row">
          <div className="col-md-8 m-auto">
            <h1 className="display-4 text-center">Sign Up</h1>
            <p className="lead text-center">Create your Account</p>
            <form action="create-profile.html" onSubmit={onSubmit}>
              <div className="form-group">
                <input
                  type="text"
                  className={"form-control form-control-lg is-invalid"}
                  placeholder="Name"
                  name="fullName"
                  onChange={onChange}
                  value={fullName}
                  required
                />
                <div className="invalid-feedback">Invalid username</div>
              </div>
              <div className="form-group">
                <input
                  type="email"
                  className="form-control form-control-lg"
                  placeholder="Email Address"
                  name="username"
                  onChange={onChange}
                  value={username}
                />
              </div>
              <div className="form-group">
                <input
                  type="password"
                  className="form-control form-control-lg"
                  placeholder="Password"
                  name="password"
                  onChange={onChange}
                  value={password}
                  required
                />
              </div>
              <div className="form-group">
                <input
                  type="password"
                  className="form-control form-control-lg"
                  placeholder="Confirm Password"
                  name="password2"
                  onChange={onChange}
                  value={password2}
                  required
                />
              </div>
              <input type="submit" className="btn btn-info btn-block mt-4" />
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default connect(null, { registerNewUser })(Register);
