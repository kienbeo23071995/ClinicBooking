import React, { Component, useState, useEffect } from 'react';
import { Button } from 'antd';
import axios from 'axios';
import moment from 'moment';
import { Table } from 'reactstrap';
import { getAllUsers } from '../util/api/call-api';
import { Modal } from 'react-bootstrap';
import { Form } from 'react-bootstrap';
function UserRes() {
    const [updateUser, setUpdateUser] = useState({});
    const [users, setUsers] = useState([]);
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = (e) => {
        setShow(true);
        setUpdateUser(e)
    };
    useEffect(() => {
        getAllUsers().then(response => {
            setUsers(response.data.object)
        })
    }, []);
    const handleDelete = (e) => {
        let confirm = window.confirm("Bạn muốn xóa tài khoản này không ? ");
        if (confirm) {
            axios.get('http://localhost:8080/api/user/delete/' + e)
                .then((response) => {
                    const data = response.data.data.object;
                    setUsers(data);
                })
                .catch((error) => {
                    console.error("Error fetching data:", error);
                })
        }

    };
    return (
        <React.Fragment>
            <h2 style={{ textAlign: 'center', marginTop: '2%', marginBottom: '2%' }}>Danh sách Tài Khoản Người Dùng</h2>
            <Table striped bordered hover >
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Full Name</th>
                        <th>User Name</th>
                        <th>Email</th>
                        <th>Date Of Birth</th>
                        <th>Gender</th>
                        <th>Role</th>
                        <th colSpan={2}>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        users.map((item, index) => (
                            <tr>
                                <td>{index + 1}</td>
                                <td>{item.fullName}</td>
                                <td>{item.username}</td>
                                <td>{item.email}</td>
                                <td>{item.birthday ? moment(item.birthday).format('MM/DD/YYYY') : ''}</td>
                                <td>{item.gender}</td>
                                <td>{
                                    item.roles.map((value) => (
                                        <div>{value.name}</div>
                                    ))
                                }</td>
                                <td>
                                    <Button onClick={() => handleShow(item)}>Detail</Button>
                                    <Button onClick={() => handleDelete(item.id)}>Delete</Button>
                                </td>
                            </tr>
                        ))
                    }
                </tbody>
            </Table>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Cập nhật thông tin tài khoản</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form id='form'>
                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>Email address</Form.Label>
                            <Form.Control type="email" name='email' placeholder="Enter email" value={updateUser.email ? updateUser.email : ''} />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formBasicUsername">
                            <Form.Label>Username</Form.Label>
                            <Form.Control type="text" name='username' placeholder="Enter username" value={updateUser.username ? updateUser.username : ''} />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formBasicBirthday">
                            <Form.Label>Birthday</Form.Label>
                            <Form.Control type="date" name='birthday' placeholder="Enter birthday" value={updateUser.birthday ? moment(updateUser.birthday).format('YYYY-MM-DD') : ''} />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formBasicfullName">
                            <Form.Label>Full Name</Form.Label>
                            <Form.Control type="text" name='fullname' placeholder="Enter fullname" value={updateUser.fullName ? updateUser.fullName : ''} />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formBasicmobile">
                            <Form.Label>Mobile</Form.Label>
                            <Form.Control type="tel" name='mobile' placeholder="Enter phone" value={updateUser.mobile ? updateUser.mobile : ''} />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formBasicmobile">
                            <Form.Control type='button' value='Close' onClick={handleClose}/>
                        </Form.Group>
                    </Form>
                </Modal.Body>
            </Modal>
        </React.Fragment>
    );
}

export default UserRes