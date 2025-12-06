import 'package:flutter/material.dart';

void main() {
  runApp(const WhtrApp());
}

class WhtrApp extends StatelessWidget {
  const WhtrApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'WHtR Calculator',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(
          seedColor: Colors.teal,
          brightness: Brightness.light,
        ),
        scaffoldBackgroundColor: Colors.white,
        useMaterial3: true,
        inputDecorationTheme: InputDecorationTheme(
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(12),
          ),
          filled: true,
          fillColor: Colors.grey.shade50,
        ),
        elevatedButtonTheme: ElevatedButtonThemeData(
          style: ElevatedButton.styleFrom(
            padding: const EdgeInsets.symmetric(vertical: 16, horizontal: 32),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(12),
            ),
            textStyle: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
          ),
        ),
      ),
      home: const WhtrHomePage(),
    );
  }
}

class WhtrHomePage extends StatefulWidget {
  const WhtrHomePage({super.key});

  @override
  State<WhtrHomePage> createState() => _WhtrHomePageState();
}

class _WhtrHomePageState extends State<WhtrHomePage> {
  final TextEditingController _waistController = TextEditingController();
  final TextEditingController _heightController = TextEditingController();
  
  double? _result;
  String? _error;

  void _calculateWhtr() {
    setState(() {
      _result = null;
      _error = null;
    });

    final String waistStr = _waistController.text;
    final String heightStr = _heightController.text;

    if (waistStr.isEmpty || heightStr.isEmpty) {
      setState(() => _error = 'Please fill in both fields.');
      return;
    }

    final double? waist = double.tryParse(waistStr);
    final double? height = double.tryParse(heightStr);

    if (waist == null || height == null) {
      setState(() => _error = 'Please enter valid numbers.');
      return;
    }

    if (waist <= 0 || height <= 0) {
      setState(() => _error = 'Values must be greater than zero.');
      return;
    }

    setState(() {
      _result = waist / height;
    });
  }

  Color _getResultColor(double ratio) {
    if (ratio < 0.35) return Colors.blue;
    if (ratio < 0.42) return Colors.cyan;
    if (ratio < 0.52) return Colors.green;
    if (ratio < 0.57) return Colors.orange;
    if (ratio < 0.62) return Colors.deepOrange;
    return Colors.red;
  }

  String _getResultInterpretation(double ratio) {
    if (ratio < 0.35) return 'Extremely Slim';
    if (ratio < 0.42) return 'Underweight';
    if (ratio < 0.52) return 'Healthy Range';
    if (ratio < 0.57) return 'Overweight';
    if (ratio < 0.62) return 'Heavy Overweight';
    return 'Obesity Risk';
  }

  @override
  void dispose() {
    _waistController.dispose();
    _heightController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Waist-to-Height Ratio'),
        centerTitle: true,
        backgroundColor: Colors.white,
        surfaceTintColor: Colors.transparent,
        elevation: 0,
        actions: [
            IconButton(onPressed: (){}, icon: const Icon(Icons.info_outline)) 
        ],
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(24.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            const SizedBox(height: 10),
            Text(
              'Calculate your WHtR',
              style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                    fontWeight: FontWeight.bold,
                    color: Colors.black87,
                  ),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 30),
            TextField(
              controller: _waistController,
              keyboardType: const TextInputType.numberWithOptions(decimal: true),
              decoration: const InputDecoration(
                labelText: 'Waist Circumference (cm)',
                prefixIcon: Icon(Icons.accessibility_new),
                hintText: 'e.g. 80',
              ),
            ),
            const SizedBox(height: 20),
            TextField(
              controller: _heightController,
              keyboardType: const TextInputType.numberWithOptions(decimal: true),
              decoration: const InputDecoration(
                labelText: 'Height (cm)',
                prefixIcon: Icon(Icons.height),
                hintText: 'e.g. 175',
              ),
            ),
            const SizedBox(height: 30),
            ElevatedButton(
              onPressed: _calculateWhtr,
              child: const Text('Calculate'),
            ),
            const SizedBox(height: 30),
            if (_error != null)
              Container(
                padding: const EdgeInsets.all(12),
                decoration: BoxDecoration(
                  color: Colors.red.shade50,
                  borderRadius: BorderRadius.circular(8),
                  border: Border.all(color: Colors.red.shade200),
                ),
                child: Text(
                  _error!,
                  style: TextStyle(color: Colors.red.shade700),
                  textAlign: TextAlign.center,
                ),
              ),
            if (_result != null) ...[
              Card(
                elevation: 0,
                color: Colors.grey.shade50,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(16),
                  side: BorderSide(
                    color: Colors.grey.shade200,
                    width: 1,
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(24.0),
                  child: Column(
                    children: [
                      Text(
                        'Your Ratio',
                        style: Theme.of(context).textTheme.titleMedium?.copyWith(
                          color: Colors.grey.shade700
                        ),
                      ),
                      const SizedBox(height: 8),
                      Text(
                        _result!.toStringAsFixed(2),
                        style: Theme.of(context).textTheme.displayMedium?.copyWith(
                              fontWeight: FontWeight.bold,
                              color: _getResultColor(_result!),
                            ),
                      ),
                      const SizedBox(height: 16),
                      Text(
                        _getResultInterpretation(_result!),
                        style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                              fontWeight: FontWeight.w500,
                              color: Colors.black87,
                            ),
                      ),
                      const SizedBox(height: 32),
                      SizedBox(
                        height: 50,
                        child: CustomPaint(
                          painter: WhtrScalePainter(ratio: _result!),
                          child: Container(),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ],
        ),
      ),
    );
  }
}

class WhtrScalePainter extends CustomPainter {
  final double ratio;

  WhtrScalePainter({required this.ratio});

  @override
  void paint(Canvas canvas, Size size) {
    final double width = size.width;
    final double height = 12.0;
    final double barY = size.height / 2 - height / 2;

    // Scale config
    const double minScale = 0.2;
    const double maxScale = 0.8;
    
    double normalize(double val) {
      if (val < minScale) return 0.0;
      if (val > maxScale) return 1.0;
      return (val - minScale) / (maxScale - minScale);
    }

    // Gradient
    final Rect rect = Rect.fromLTWH(0, barY, width, height);
    final Paint gradientPaint = Paint()
      ..shader = LinearGradient(
        colors: const [
          Colors.blue,
          Colors.cyan,
          Colors.green,
          Colors.orange,
          Colors.deepOrange,
          Colors.red
        ],
        stops: [
          normalize(0.30),
          normalize(0.38),
          normalize(0.47),
          normalize(0.55),
          normalize(0.60),
          normalize(0.70),
        ],
      ).createShader(rect);

    // Rounded background cap
    canvas.drawRRect(
      RRect.fromRectAndRadius(rect, const Radius.circular(6)),
      gradientPaint,
    );

    // Indicator
    final double indicatorX = normalize(ratio) * width;
    final Paint indicatorPaint = Paint()
      ..color = Colors.black87
      ..style = PaintingStyle.fill;
      
    const double triangleSize = 8.0;
    
    // Draw Triangle Marker below
    final Path path = Path();
    path.moveTo(indicatorX, barY + height + 2);
    path.lineTo(indicatorX - triangleSize/2, barY + height + 2 + triangleSize);
    path.lineTo(indicatorX + triangleSize/2, barY + height + 2 + triangleSize);
    path.close();
    
    canvas.drawPath(path, indicatorPaint);
    
    // Draw Triangle Marker above
    final Path pathAbove = Path();
    pathAbove.moveTo(indicatorX, barY - 2);
    pathAbove.lineTo(indicatorX - triangleSize/2, barY - 2 - triangleSize);
    pathAbove.lineTo(indicatorX + triangleSize/2, barY - 2 - triangleSize);
    pathAbove.close();
    
    canvas.drawPath(pathAbove, indicatorPaint);
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) => true;
}
