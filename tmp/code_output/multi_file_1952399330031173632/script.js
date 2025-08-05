// script.js
document.addEventListener('DOMContentLoaded', function() {
    // 获取DOM元素
    const messageForm = document.getElementById('messageForm');
    const messagesContainer = document.getElementById('messagesContainer');
    
    // 初始化留言数组
    let messages = JSON.parse(localStorage.getItem('messages')) || [];
    
    // 渲染留言列表
    function renderMessages() {
        messagesContainer.innerHTML = '';
        
        if (messages.length === 0) {
            messagesContainer.innerHTML = '<p class="no-messages">暂无留言，快来发表第一条吧！</p>';
            return;
        }
        
        messages.forEach((message, index) => {
            const messageElement = document.createElement('div');
            messageElement.className = 'message';
            messageElement.innerHTML = `
                <div class="message-header">
                    <span class="message-author">${message.name}</span>
                    <span class="message-date">${formatDate(message.date)}</span>
                </div>
                <div class="message-content">${message.content}</div>
            `;
            messagesContainer.appendChild(messageElement);
        });
    }
    
    // 格式化日期
    function formatDate(dateString) {
        const date = new Date(dateString);
        return date.toLocaleString('zh-CN', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit'
        });
    }
    
    // 处理表单提交
    messageForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const name = document.getElementById('name').value.trim();
        const content = document.getElementById('content').value.trim();
        
        if (name && content) {
            // 创建新留言
            const newMessage = {
                name,
                content,
                date: new Date().toISOString()
            };
            
            // 添加到留言数组
            messages.unshift(newMessage);
            
            // 保存到本地存储
            localStorage.setItem('messages', JSON.stringify(messages));
            
            // 重新渲染留言列表
            renderMessages();
            
            // 重置表单
            messageForm.reset();
        }
    });
    
    // 初始渲染
    renderMessages();
});