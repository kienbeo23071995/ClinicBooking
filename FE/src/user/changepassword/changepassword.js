import {
    EMAIL_MAX_LENGTH,
    PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH
} from '../../constants';
import React, { Component } from 'react';
import { updatepassword } from "../../util/APIUtils";
import { Form, Input, Button, notification } from 'antd';
const FormItem = Form.Item
class changepassword extends Component {
    render() {
        const AntWrappedChangepassForm = Form.create()(ChangepassForm)
        return (
            <AntWrappedChangepassForm history={this.props.history} />
        );
    }
}

class ChangepassForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            confirmDirty: false,
            value: ""
        }
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                const resetPassRequest = Object.assign({}, values);
                updatepassword(resetPassRequest)
                    .then(response => {
                        if (response != null) {
                            notification.success({
                                message: 'Đổi mật khẩu',
                                description: "Đổi mật khẩu thành công",
                            });

                            this.props.history.push("/");
                        }
                    }).catch(error => {
                        notification.error({
                            message: 'Đổi mật khẩu',
                            description: error.message || 'Xin lỗi bạn ! Đổi mật khẩu không thành công !'
                        });
                    });
            }
        });
    }

    validateToNextPassword = (rule, value, callback) => {
        const { form } = this.props;
        if (value && this.state.confirmDirty) {
            form.validateFields(['confirm_password'], { force: true });
        }
        callback();
    };

    compareToFirstPassword = (rule, value, callback) => {
        const { form } = this.props;
        if (value && value !== form.getFieldValue('password')) {
            callback('Mật khẩu mà bạn nhập không trùng khớp!');
        } else {
            callback();
        }
    };

    validateEmail = (rule, email, callback) => {
        const EMAIL_REGEX = RegExp('[^@ ]+@[^@ ]+\\.[^@ ]+');
        if (!email) {
            callback("Email không được trống !");
        }

        if (!EMAIL_REGEX.test(email)) {
            callback("Email không đúng định dạng !");
        }

        if (email.length > EMAIL_MAX_LENGTH) {
            callback("Email quá lớn !. Bạn cần nhập nhỏ hơn( " + EMAIL_MAX_LENGTH + " ) ký tự! !");
        } else {
            callback();
        }
    }

    handleConfirmBlur = e => {
        const { value } = e.target;
        this.setState({ confirmDirty: this.state.confirmDirty || !!value });
    };

    validatePassword = (rule, password, callback) => {
        if (password.length < PASSWORD_MIN_LENGTH) {
            callback("Password quá nhỏ !. Bạn cần nhập lớn hơn ( " + PASSWORD_MIN_LENGTH + " ) ký tự !");
        } else if (password.length > PASSWORD_MAX_LENGTH) {
            callback("Password quá lớn !. Bạn cần nhập nhỏ hơn ( " + PASSWORD_MAX_LENGTH + " ) ký tự !");
        } else {
            callback();
        }
    }


    render() {
        const { getFieldDecorator } = this.props.form;
        return (
            <div className="signup-container">
                <h1 className="page-title">Reset Password</h1>
                <div className="signup-content">
                    <Form onSubmit={this.handleSubmit} className="signup-form">
                        <FormItem className="row-file"
                            label={
                                <span> <strong>E-mail</strong></span>
                            }>
                            {getFieldDecorator('email', {
                                rules: [
                                    {
                                        type: 'email',
                                        message: 'Lỗi định dạng E-mail!',
                                    },
                                    {
                                        required: true,
                                        message: 'Hãy nhập E-mail!',
                                    },
                                    {
                                        validator: this.validateEmail,
                                    }
                                ],
                            })(<Input
                                size="large"
                                name="email"
                                autoComplete="off"
                                placeholder="Hãy nhập email của bạn !"
                            />)}
                        </FormItem>
                        <FormItem className="row-file"
                            label={
                                <span> <strong>Old Password</strong></span>
                            } hasFeedback>

                            {getFieldDecorator('oldpass', {
                                rules: [
                                    {
                                        required: true,
                                        message: 'Hãy nhập mật khẩu của bạn !',
                                    },
                                    {
                                        validator: this.validatePassword,
                                    }
                                ],
                            })(<Input.Password
                                size="large"
                                name="oldpass"
                                autoComplete="off"
                                placeholder="Hãy nhập mật khẩu của bạn !"
                            />)}
                        </FormItem>
                        <FormItem className="row-file"
                            label={
                                <span> <strong>Password</strong></span>
                            } hasFeedback>

                            {getFieldDecorator('password', {
                                rules: [
                                    {
                                        required: true,
                                        message: 'Hãy nhập mật khẩu của bạn !',
                                    },
                                    {
                                        validator: this.validateToNextPassword,
                                    },
                                    {
                                        validator: this.validatePassword,
                                    }
                                ],
                            })(<Input.Password
                                size="large"
                                name="password"
                                autoComplete="off"
                                placeholder="Hãy nhập mật khẩu của bạn !"
                            />)}
                        </FormItem>
                        <FormItem className="row-file" label={
                            <span> <strong>Confirm Password</strong></span>
                        } hasFeedback>
                            {getFieldDecorator('confirm_password', {
                                rules: [
                                    {
                                        required: true,
                                        message: 'Hãy nhập lại mật khẩu !',
                                    },
                                    {
                                        validator: this.compareToFirstPassword,
                                    },
                                ],
                            })(<Input.Password placeholder="Nhập lại mật khẩu !" size="large" onBlur={this.handleConfirmBlur} />)}
                        </FormItem>
                        <FormItem>
                            <Button type="primary"
                                htmlType="submit"
                                size="large"
                                className="signup-form-button">Đổi Mật Khẩu</Button>
                        </FormItem>
                    </Form>
                </div>
            </div>
        );
    }
}

export default changepassword;