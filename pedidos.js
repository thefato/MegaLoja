document.addEventListener('DOMContentLoaded', () => {

    // --- SIMULAÇÃO DE BANCO DE DADOS ---
    
    // Gera dados iniciais se não existirem no LocalStorage
    function initMockData() {
        if (!localStorage.getItem('megaloja_orders')) {
            const mockOrders = [
                {
                    id: 1001,
                    date: '2023-10-25',
                    items: 'Notebook Gamer (1x)',
                    total: 1999.90,
                    status: 'concluido', // pendente, processando, concluido, cancelado
                    userId: 1 // Simula o usuário logado
                },
                {
                    id: 1002,
                    date: '2023-10-26',
                    items: 'Mouse Sem Fio (2x), Teclado Mecânico (1x)',
                    total: 350.00,
                    status: 'processando',
                    userId: 1
                },
                {
                    id: 1003,
                    date: '2023-10-27',
                    items: 'Monitor 24pol (1x)',
                    total: 890.00,
                    status: 'pendente',
                    userId: 2 // Outro usuário (não deve aparecer na tela 'Meus Pedidos')
                }
            ];
            localStorage.setItem('megaloja_orders', JSON.stringify(mockOrders));
        }
    }

    initMockData(); // Roda a inicialização

    // Carrega pedidos
    let orders = JSON.parse(localStorage.getItem('megaloja_orders')) || [];
    
    // Elementos DOM
    const userOrdersList = document.getElementById('user-orders-list');
    const adminOrdersList = document.getElementById('admin-orders-list');
    
    // ID do usuário "Logado" (Fixo para teste)
    const CURRENT_USER_ID = 1; 

    // --- FUNÇÕES AUXILIARES ---

    function formatCurrency(value) {
        return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value);
    }

    function getStatusLabel(status) {
        switch(status) {
            case 'pendente': return 'Pendente';
            case 'processando': return 'Em Processamento';
            case 'concluido': return 'Concluído';
            case 'cancelado': return 'Cancelado';
            default: return status;
        }
    }

    // --- RENDERIZAÇÃO: PÁGINA DO USUÁRIO ---
    
    if (userOrdersList) {
        userOrdersList.innerHTML = '';
        
        // Filtra apenas os pedidos do usuário logado
        const myOrders = orders.filter(order => order.userId === CURRENT_USER_ID);
        
        // Ordena por ID decrescente (mais recente primeiro)
        myOrders.sort((a, b) => b.id - a.id);

        if (myOrders.length === 0) {
            userOrdersList.innerHTML = '<p>Você ainda não fez nenhum pedido.</p>';
        } else {
            myOrders.forEach(order => {
                const card = document.createElement('div');
                card.className = 'order-card';
                
                card.innerHTML = `
                    <div class="order-info">
                        <h3>Pedido #${order.id}</h3>
                        <p>Data: ${order.date}</p>
                        <p>Itens: ${order.items}</p>
                        <p style="margin-top: 5px;">Total: <strong>${formatCurrency(order.total)}</strong></p>
                    </div>
                    <div>
                        <span class="status-badge status-${order.status}">
                            ${getStatusLabel(order.status)}
                        </span>
                    </div>
                `;
                userOrdersList.appendChild(card);
            });
        }
    }

    // --- RENDERIZAÇÃO: PÁGINA DO ADMIN ---

    if (adminOrdersList) {
        renderAdminList();
    }

    function renderAdminList() {
        adminOrdersList.innerHTML = '';
        
        // Admin vê TUDO (sem filtro de user), ordenado por ID
        const allOrders = [...orders].sort((a, b) => b.id - a.id);

        allOrders.forEach(order => {
            const card = document.createElement('div');
            card.className = 'order-card';
            
            // HTML do Select pré-selecionado com o status atual
            const selectHTML = `
                <select id="status-${order.id}">
                    <option value="pendente" ${order.status === 'pendente' ? 'selected' : ''}>Pendente</option>
                    <option value="processando" ${order.status === 'processando' ? 'selected' : ''}>Em Processamento</option>
                    <option value="concluido" ${order.status === 'concluido' ? 'selected' : ''}>Concluído</option>
                    <option value="cancelado" ${order.status === 'cancelado' ? 'selected' : ''}>Cancelado</option>
                </select>
            `;

            card.innerHTML = `
                <div class="order-info">
                    <h3>Pedido #${order.id} <small>(User ID: ${order.userId})</small></h3>
                    <p>Itens: ${order.items}</p>
                    <p>Total: <strong>${formatCurrency(order.total)}</strong></p>
                    <div style="margin-top: 8px;">
                        Status Atual: 
                        <span class="status-badge status-${order.status}">
                            ${getStatusLabel(order.status)}
                        </span>
                    </div>
                </div>
                
                <div class="admin-order-actions">
                    <label>Alterar para:</label>
                    ${selectHTML}
                    <button class="btn-update" onclick="updateOrderStatus(${order.id})">Atualizar</button>
                </div>
            `;
            adminOrdersList.appendChild(card);
        });
    }

    // --- LÓGICA DE ATUALIZAÇÃO (GLOBAL) ---
    
    // Precisamos expor essa função para o escopo global para o 'onclick' funcionar
    window.updateOrderStatus = function(orderId) {
        const select = document.getElementById(`status-${orderId}`);
        const newStatus = select.value;

        // Encontra o pedido e atualiza
        const orderIndex = orders.findIndex(o => o.id === orderId);
        if (orderIndex !== -1) {
            orders[orderIndex].status = newStatus;
            
            // Salva no LocalStorage
            localStorage.setItem('megaloja_orders', JSON.stringify(orders));
            
            alert(`Status do pedido #${orderId} atualizado para: ${getStatusLabel(newStatus)}`);
            
            // Re-renderiza a lista para atualizar a cor da badge visualmente
            renderAdminList();
        }
    };

});