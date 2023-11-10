import React,{ Component } from 'react';
import './resetpass.css';
import { resetpassword } from '../../util/APIUtils';
import { 
    EMAIL_MAX_LENGTH
} from '../../constants';
import { Form, Input, Button, notification } from 'antd';
const FormItem = Form.Item;
class Resetpass extends Component {
    render() {
        const AntWrappedResetpassForm = Form.create()(ResetpassForm)
        return (
            <AntWrappedResetpassForm history = {this.props.history}/>
        );
    }
}

class ResetpassForm extends Component {
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
                resetpassword(resetPassRequest)
                    .then(response => {
                        if (response.status === 200) {
                            notification.success({
                                message: 'Đặt lại mật khẩu',
                                description: "New passowrd đã được gửi vào tài khoản của bạn! Xin hãy kiểm tra email",
                            });

                            this.props.history.push("/login");
                        }
                    }).catch(error => {
                        notification.error({
                            message: 'Đặt lại mật khẩu',
                            description: error.message || 'Xin lỗi bạn ! Email không tồn tại !'
                        });
                    });
            }
        });
    }

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
                        <FormItem>
                            <Button type="primary"
                                htmlType="submit"
                                size="large"
                                className="signup-form-button">Gửi Email</Button>
                        </FormItem>
                    </Form>
                </div>
            </div>
        );
    }
}

export default Resetpass;