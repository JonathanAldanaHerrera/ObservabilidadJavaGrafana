import random
import uuid
from datetime import datetime, timedelta

# Definir los endpoints
endpoints = [
    ('USER001', 'GET', '/api/users', 1),  # Related to User Service
    ('USER002', 'GET', '/api/users/{id}', 1),  # Related to User Service
    ('USER003', 'POST', '/api/users', 1),  # Related to User Service
    ('USER004', 'PUT', '/api/users/{id}', 1),  # Related to User Service
    ('USER005', 'DELETE', '/api/users/{id}', 1),  # Related to User Service

    ('PAY001', 'POST', '/api/payments', 2),  # Related to Payment Service
    ('PAY002', 'GET', '/api/payments/{id}', 2),  # Related to Payment Service
    ('PAY003', 'PUT', '/api/payments/{id}', 2),  # Related to Payment Service
    ('PAY004', 'DELETE', '/api/payments/{id}', 2),  # Related to Payment Service

    ('ORDER001', 'POST', '/api/orders', 3),  # Related to Order Service
    ('ORDER002', 'GET', '/api/orders/{id}', 3),  # Related to Order Service
    ('ORDER003', 'PUT', '/api/orders/{id}', 3),  # Related to Order Service
    ('ORDER004', 'DELETE', '/api/orders/{id}', 3),  # Related to Order Service
    ('ORDER005', 'GET', '/api/orders/user/{user_id}', 3),  # Related to Order Service

    ('INV001', 'GET', '/api/inventory', 4),  # Related to Inventory Service
    ('INV002', 'GET', '/api/inventory/{product_id}', 4),  # Related to Inventory Service
    ('INV003', 'PUT', '/api/inventory/{product_id}', 4),  # Related to Inventory Service

    ('SHIP001', 'POST', '/api/shipping', 5),  # Related to Shipping Service
    ('SHIP002', 'GET', '/api/shipping/{order_id}', 5),  # Related to Shipping Service
    ('SHIP003', 'PUT', '/api/shipping/{order_id}', 5),  # Related to Shipping Service
    ('SHIP004', 'DELETE', '/api/shipping/{order_id}', 5),  # Related to Shipping Service

    ('PROD001', 'GET', '/api/products', 6),  # Related to Product Service
    ('PROD002', 'GET', '/api/products/{id}', 6),  # Related to Product Service
    ('PROD003', 'POST', '/api/products', 6),  # Related to Product Service
    ('PROD004', 'PUT', '/api/products/{id}', 6),  # Related to Product Service
    ('PROD005', 'DELETE', '/api/products/{id}', 6),  # Related to Product Service

    ('AUTH001', 'POST', '/api/auth/login', 7),  # Related to Authentication Service
    ('AUTH002', 'POST', '/api/auth/register', 7),  # Related to Authentication Service
    ('AUTH003', 'POST', '/api/auth/logout', 7),  # Related to Authentication Service

    ('SUP001', 'POST', '/api/support/tickets', 8),  # Related to Customer Support Service
    ('SUP002', 'GET', '/api/support/tickets/{ticket_id}', 8),  # Related to Customer Support Service
    ('SUP003', 'PUT', '/api/support/tickets/{ticket_id}', 8),  # Related to Customer Support Service
    ('SUP004', 'DELETE', '/api/support/tickets/{ticket_id}', 8)  # Related to Customer Support Service
]


# Definir los códigos de estado
status_codes = {
    '2xx': [200, 201, 202, 204],
    '4xx': [400, 401, 403, 404],
    '5xx': [500, 502, 503, 504]
}

# Porcentaje de distribución de los códigos de estado
status_distribution = {
    '2xx': 60,
    '4xx': 35,
    '5xx': 5
}

# Función para generar el SQL
def generate_log_sql(total_logs=1000):
    logs = []
    
    # Calcular cuántos logs deben corresponder a cada tipo de estado
    num_2xx = int((status_distribution['2xx'] / 100) * total_logs)
    num_4xx = int((status_distribution['4xx'] / 100) * total_logs)
    num_5xx = total_logs - num_2xx - num_4xx  # Asegurar que el total sea consistente

    # Contadores para cada tipo de estado
    count_2xx, count_4xx, count_5xx = 0, 0, 0

    for i in range(1, total_logs + 1):
        # Escoger el código de estado basado en la distribución calculada
        if count_2xx < num_2xx:
            status_code = random.choice(status_codes['2xx'])
            count_2xx += 1
        elif count_4xx < num_4xx:
            status_code = random.choice(status_codes['4xx'])
            count_4xx += 1
        else:
            status_code = random.choice(status_codes['5xx'])
            count_5xx += 1

        # Escoger un endpoint aleatorio
        endpoint = random.choice(endpoints)
        method = endpoint[1]
        path = endpoint[2]

        # Generar un UUID para RequestId y ConnectionId
        request_id = str(uuid.uuid4())
        connection_id = str(uuid.uuid4())

        # Generar la fecha actual y añadir una pequeña variación
        current_time = datetime.now().astimezone() + timedelta(minutes=i)
        timestamp = current_time.strftime('%Y-%m-%d %H:%M:%S')
        timestamp_final = current_time.strftime('%Y-%m-%d %H:%M:%S')

        # Generar el log SQL con la sintaxis especificada
        log_entry = f"('{timestamp}',"
        log_entry += f"'Information', "
        log_entry += f"'Request {method} {path}',"
        log_entry += f"'{{\"Protocol\": \"HTTP/1.1\", \"Method\": \"{method}\", \"ContentType\": null, "
        log_entry += f"\"ContentLength\": null, \"Scheme\": \"http\", \"Host\": \"localhost:9091\", \"PathBase\": \"\", "
        log_entry += f"\"Path\": \"{path}\", \"StatusCode\": {status_code}, \"QueryString\": \"\", "
        log_entry += f"\"HostingRequestStartingLog\": \"Request starting HTTP/1.1 {method} http://localhost:9091{path} - -\", "
        log_entry += f"\"EventId\": {{\"Id\": {i}}}, \"SourceContext\": \"Microsoft.AspNetCore.Hosting.Diagnostics\", "
        log_entry += f"\"RequestId\": \"{request_id}\", \"RequestPath\": \"{path}\", \"ConnectionId\": \"{connection_id}\"}}', "
        log_entry += f"'',"
        log_entry += f"'',"
        log_entry += f"'{timestamp_final}')"

        logs.append(log_entry)

    return logs

# Guardar los logs generados en un archivo .txt dentro de la carpeta "Scripts"
def save_logs_to_file(filename='logs.txt', folder_path='Scripts'):
    # Ruta completa del archivo donde se guardarán los logs
    full_path = folder_path + '/' + filename

    logs = generate_log_sql(total_logs=1000)
    with open(full_path, 'w') as file:
        file.write(
            'INSERT INTO `logs` (Timestamp, Level, Template, Message, Exception, Properties, _ts) VALUES \n'
        )
        file.write(',\n'.join(logs) + ';')

# Ejecutar la función para guardar los logs en la carpeta "Scripts"
save_logs_to_file(filename='logs.txt', folder_path='Scripts')
print(f"Archivo 'logs.txt' generado exitosamente en la carpeta 'Scripts'.")
