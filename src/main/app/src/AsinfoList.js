import React, { Component }  from 'react';
import { Button, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';

class AsinfoList extends Component {


    constructor(props) {
        super(props);
        this.state = { groups: [], isLoading: true };
    }

    componentDidMount() {
        this.setState({ isLoading: true });

        fetch('/graphas/api/country/RS')
            .then(response => response.json())
            .then(data => this.setState({ groups: data, isLoading: false }));
    }

    render() {
        const { groups, isLoading } = this.state;

        if (isLoading) {
            return <p>Loading...</p>
        }


        const asinfoList = groups.map(group => {
            return <tr key={group.id}>
                <td>
                    {group.number}
                </td>
                <td>
                    <Button>
                        More Info
                    </Button>
                </td>
            </tr>
        });


        return (
            <div>
                <AppNavbar />
                <Container fluid>
                    <h3>List of AS numbers</h3>
                    <Table className="mt-4">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th width="20%">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {asinfoList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default AsinfoList;

