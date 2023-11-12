import React, { Component } from 'react';
import { Form, Input, Button, Icon, notification } from 'antd';
const FormItem = Form.Item;
class adminLogin extends Component {
    render() {
        const AntWrappedLoginForm = Form.create()(AdminLoginForm)
        return (
            <div className="login-container">
                <h1 className="page-signin">Đăng Nhập ADMIN</h1>
                <div className="login-content">
                    <AntWrappedLoginForm />
                </div>
            </div>
        );
    }
}

class AdminLoginForm extends Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }  
    handleSubmit(event) {
        event.preventDefault();
        let username = document.getElementById("adminname").value;
        let password = document.getElementById("passwordname").value;
        if (username == 'admin' && password == 'admin') {
            notification.success({
                message: 'Booking Clinic',
                description: 'Đăng nhập tài khoản admin thành công'
            });
            window.location.href = "http://localhost:3000/admin/user";
        }
        else {
            notification.error({
                message: 'Booking Clinic',
                description: 'Tài khoản hoặc mật khẩu không đúng'
            });
        }
    }

    render() {
        return (
            <Form onSubmit={this.handleSubmit} className="login-form">
                <FormItem>
                    {
                        <Input
                            prefix={<Icon type="user" />}
                            size="large"
                            name="username"
                            id='adminname'
                            placeholder="UserName" />
                    }
                </FormItem>
                <FormItem>
                    {
                        <Input
                            prefix={<Icon type="lock" />}
                            size="large"
                            name="password"
                            type="password"
                            id='passwordname'
                            placeholder="Password" />
                    }
                </FormItem>
                <FormItem>
                    <Button type="primary" htmlType="submit" size="large" className="login-form-button">Đăng nhập</Button>
                </FormItem>
            </Form>
        )
    }
}
export default adminLogin