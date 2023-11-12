import React, { Component, useState, useEffect } from 'react';
import { Table } from 'reactstrap';
import { getAllClinics } from '../util/api/call-api';

function AdminClinic() {
    const [clinics, setClinics] = useState([]);
    useEffect(() => {
        getAllClinics().then(response => {
            setClinics(response.data.object)
        })
    }, []);
    return (
        <React.Fragment>
            <h2 style={{ textAlign: 'center', marginTop: '2%', marginBottom: '2%' }}>Danh sách Phòng Bệnh</h2>
            <Table striped bordered hover >
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th>Address</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        clinics.map((item, index) => (
                            <tr>
                                <td>{index + 1}</td>
                                <td>{item.name}</td>
                                <td>{item.address}</td>
                            </tr>
                        ))
                    }
                </tbody>
            </Table>
        </React.Fragment>
    );
}

export default AdminClinic