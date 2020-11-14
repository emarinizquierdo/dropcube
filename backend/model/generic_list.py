class GenericList:
    cursor = None
    data = []
    total = None

    def __init__(self, cursor, data, total):
        self.cursor = cursor
        self.data = data
        self.total = total